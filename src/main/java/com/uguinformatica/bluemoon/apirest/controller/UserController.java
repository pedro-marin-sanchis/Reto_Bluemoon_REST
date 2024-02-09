package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.*;
import com.uguinformatica.bluemoon.apirest.models.dto.*;
import com.uguinformatica.bluemoon.apirest.models.entity.*;
import com.uguinformatica.bluemoon.apirest.mappers.UserRegisterDtoMapper;
import com.uguinformatica.bluemoon.apirest.models.entity.keys.CartKey;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDAOImpl userService;

    @Autowired
    private ProductDAOImpl productService;

    @Autowired
    private CartDAOImpl cartService;
    @Autowired
    private TradeDAOImpl tradeService;

    @Autowired
    private SilverTypeDAOImpl silverTypeService;

    @Autowired
    private TradeableDAOImpl tradeableService;

    @Autowired RoleDAOImpl roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<User>> showAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal  or #username == 'me'")
    public ResponseEntity<?> show(@PathVariable String username) {

        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    private User getUserIfMe() {
        String requestUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.findByUsername(requestUsername);
    }

    @PostMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> create(@RequestBody @Valid UserRegisterDTO user, BindingResult result) {

        if (userService.findByUsername(user.username) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userService.findByEmail(user.email) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }

        Role role = roleService.findByRoleName("USER");

        User userEntity = UserRegisterDtoMapper.map(user);

        userEntity.setRolesAssociated(Set.of(role));
        userEntity.setPassword(passwordEncoder.encode(user.password));

        userService.save(userEntity);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> update(@PathVariable String username, @RequestBody @Valid UpdateUserEntity newUser, BindingResult result) {

        User userFound = userService.findByUsername(username.equals("me") ? SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() : username);


        if (userFound == null) {
            return ResponseEntity.notFound().build();
        }
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }
        if (!newUser.username.equals(userFound.getUsername()) && userService.findByUsername(newUser.username) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        userFound.setUsername(newUser.username);
        userFound.setName(newUser.name);
        userFound.setSurnames(newUser.surnames);
        userFound.setEmail(newUser.email);
        userFound.setAddress(newUser.address);
        userFound.setBalance(newUser.balance);

        userService.update(userFound);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{username}/password")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody UpdatePassword password) {

        String actualUsername = username.equals("me") ? SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() : username;

        User user = userService.findByUsername(actualUsername);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setPassword(passwordEncoder.encode(password.password));
        userService.update(user);

        return ResponseEntity.ok().build();
    }

    // ----------------- Cart -----------------
    @GetMapping("/{username}/cart-items")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showCartItems(@PathVariable String username) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getProductsInCart());

    }

    @PostMapping("/{username}/cart-items")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> addToCart(@PathVariable String username, @RequestBody @Valid CartAddItem cartAddData, BindingResult result) {

        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }

        CartItem cartItem = new CartItem();

        CartKey cartKey = new CartKey(cartAddData.productId, user.getId());


        cartItem.setId(cartKey);

        cartItem.setQuantity(cartAddData.quantity);
        cartItem.setProduct(productService.findById(cartAddData.productId));
        cartItem.setUser(user);

        cartService.save(cartItem);

        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/{username}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showCartItem(@PathVariable String username, @PathVariable long productId) {

        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        CartKey cartKey = new CartKey(productId, user.getId());

        CartItem cartItem = cartService.findByKey(cartKey);

        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cartItem);

    }

    @PutMapping("/{username}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> updateCartItem(
            @PathVariable String username,
            @PathVariable long productId,
            @RequestBody @Valid CartItemUpdate cartItemUpdate,
            BindingResult result
    ) {

        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }

        CartKey cartKey = new CartKey(productId, user.getId());

        CartItem cartItem = cartService.findByKey(cartKey);

        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }

        cartItem.setQuantity(cartItemUpdate.quantity);

        cartService.update(cartItem);

        return ResponseEntity.ok(cartItem);
    }


    @DeleteMapping("/{username}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> deleteCartItem(@PathVariable String username, @PathVariable long productId) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        CartKey cartKey = new CartKey(productId, user.getId());

        cartService.delete(cartService.findByKey(cartKey));
        return ResponseEntity.noContent().build();
    }


    // ----------------- Orders -----------------
    @GetMapping("/{username}/orders")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showOrders(@PathVariable String username) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getOrders());
    }

    @GetMapping("/{username}/orders/{orderId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showOrder(@PathVariable String username, @PathVariable long orderId) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getOrders().stream().filter(order -> order.getId() == orderId).findFirst().orElse(null));
    }

    // ----------------- Trades -----------------

    @GetMapping("/{username}/trades")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showTrades(@PathVariable String username) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getTrades());
    }

    @GetMapping("/{username}/trades/{tradeId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> showTrade(@PathVariable String username, @PathVariable long tradeId) {
        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getTrades().stream().filter(trade -> trade.getId() == tradeId).findFirst().orElse(null));
    }

    @PostMapping("/{username}/trades")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal or #username == 'me'")
    public ResponseEntity<?> createTrade(@PathVariable String username, @RequestBody @Valid UserTradeCreateEntity tradeCreate, BindingResult result) {

        User user = username.equals("me") ? getUserIfMe() : userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }

        Trade trade = new Trade();

        trade.setUser(user);
        trade.setDate(new Date());

        trade.setTradeables(tradeCreate.tradeables.stream().map((tradeable) -> {
            Tradeable newTradeable = new Tradeable();
            newTradeable.setWeight(tradeable.weight);
            newTradeable.setDescription(tradeable.description);

            newTradeable.setSilverType(silverTypeService.findById(tradeable.silverTypeId));

            newTradeable.setSellPrice(tradeable.weight * newTradeable.getSilverType().getCurrentPrice());

            return tradeableService.save(newTradeable);

        }).collect(Collectors.toSet()));
        tradeService.save(trade);

        return ResponseEntity.ok(user.getTrades());
    }

}