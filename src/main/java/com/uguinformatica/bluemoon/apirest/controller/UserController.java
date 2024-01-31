package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.UserRegisterDTO;
import com.uguinformatica.bluemoon.apirest.models.entity.User;
import com.uguinformatica.bluemoon.apirest.mappers.UserRegisterDtoMapper;
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

        System.out.println(user.getProductsInCart());

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
}