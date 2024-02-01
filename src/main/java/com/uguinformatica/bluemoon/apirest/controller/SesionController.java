package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.LoginPost;
import com.uguinformatica.bluemoon.apirest.models.entity.User;
import com.uguinformatica.bluemoon.apirest.models.security.JWTAuthtenticationConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SesionController {

    @Autowired
    private UserDAOImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTAuthtenticationConfig authtenticationConfig;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginPost body, BindingResult result) {

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }

        User user = userService.findByUsername(body.username);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (!passwordEncoder.matches(body.password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }

        String token = authtenticationConfig.getJWTToken(user);

        return ResponseEntity.ok(token);

    }

}
