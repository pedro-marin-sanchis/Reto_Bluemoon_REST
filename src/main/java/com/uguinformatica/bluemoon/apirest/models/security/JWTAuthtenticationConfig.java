package com.uguinformatica.bluemoon.apirest.models.security;

import com.uguinformatica.bluemoon.apirest.models.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JWTAuthtenticationConfig {
    private static final String TOKEN_BEARER_PREFIX = "Bearer ";
    private static final String SUPER_SECRET_KEY = "mySecretKey";

    public String getJWTToken(User user) {
        String secretKey = SUPER_SECRET_KEY;

        System.out.println( "'"+String.join(",", user.getRolesAssociated()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toList())) + "'"
        );

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(String.join(",", user.getRolesAssociated()
                        .stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList()))
                );

        String token = Jwts
                .builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return TOKEN_BEARER_PREFIX + token;
    }
}