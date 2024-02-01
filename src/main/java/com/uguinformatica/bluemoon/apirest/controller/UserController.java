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

    @GetMapping("/")
    public ResponseEntity<List<User>> showAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable String id) {


        User user = null;

        try {
            long idLong = Long.parseLong(id);

            user = userService.findById(idLong);

        } catch (NumberFormatException e) {
            //Do nothing
        } finally {
            if (user == null) {
                user = userService.findByUsername(id);
            }
        }

        if (user == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
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

        userService.save(userEntity);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid User user, BindingResult result) {
        User userFound = userService.findById(id);

        if (userFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }

        user.setId(id);

        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/cart-items")
    public ResponseEntity<?> showCartItems(@PathVariable long id) {

        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getProductsInCart());

    }

    @PostMapping("/{id}/cart-items")
    public ResponseEntity<?> addToCart(@PathVariable long id, @RequestBody @Valid CartAddItem cartAddData, BindingResult result) {

        User user = userService.findById(id);

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
    public ResponseEntity<?> showCartItem(@PathVariable long id, @PathVariable long productId) {

        User user = userService.findById(id);

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
    public ResponseEntity<?> updateCartItem(
            @PathVariable long id,
            @PathVariable long productId,
            @RequestBody @Valid CartItemUpdate cartItemUpdate,
            BindingResult result
    ) {

        User user = userService.findById(id);

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
    public ResponseEntity<?> deleteCartItem(@PathVariable long id, @PathVariable long productId) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        CartKey cartKey = new CartKey(productId, user.getId());

        cartService.delete(cartService.findByKey(cartKey));
        return ResponseEntity.noContent().build();
    }
}