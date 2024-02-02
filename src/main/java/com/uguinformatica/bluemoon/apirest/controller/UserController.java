package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.CartDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.ProductDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.CartAddItem;
import com.uguinformatica.bluemoon.apirest.models.dto.CartItemUpdate;
import com.uguinformatica.bluemoon.apirest.models.dto.UserRegisterDTO;
import com.uguinformatica.bluemoon.apirest.models.entity.CartItem;
import com.uguinformatica.bluemoon.apirest.models.entity.User;
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

import java.util.List;

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
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<User>> showAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal ") // or #username == principal
    public ResponseEntity<?> show(@PathVariable String username) {

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        /*if (username.equals("me")) {
            String requestUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = userService.findByUsername(requestUsername);
            return ResponseEntity.ok(user);
        }*/

        User user = null;

        user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
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

        User userEntity = UserRegisterDtoMapper.map(user);

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
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> update(@PathVariable String username, @RequestBody @Valid User user, BindingResult result) {
        User userFound = userService.findByUsername(username);

        if (userFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }

        user.setId(userFound.getId());

        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}/cart-items")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> showCartItems(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getProductsInCart());

    }

    @PostMapping("/{username}/cart-items")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> addToCart(@PathVariable String username, @RequestBody @Valid CartAddItem cartAddData, BindingResult result) {

        User user = userService.findByUsername(username);

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

    @GetMapping("/{id}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> showCartItem(@PathVariable String username, @PathVariable long productId) {

        User user = userService.findByUsername(username);

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

    @PutMapping("/{id}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> updateCartItem(
            @PathVariable String username,
            @PathVariable long productId,
            @RequestBody @Valid CartItemUpdate cartItemUpdate,
            BindingResult result
    ) {

        User user = userService.findByUsername(username);

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


    @DeleteMapping("/{id}/cart-items/{productId}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == principal")
    public ResponseEntity<?> deleteCartItem(@PathVariable String username, @PathVariable long productId) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        CartKey cartKey = new CartKey(productId, user.getId());

        cartService.delete(cartService.findByKey(cartKey));
        return ResponseEntity.noContent().build();
    }
}