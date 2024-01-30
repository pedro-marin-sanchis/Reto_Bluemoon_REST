package com.uguinformatica.bluemoon_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uguinformatica.bluemoon_api.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon_api.models.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDAOImpl userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
