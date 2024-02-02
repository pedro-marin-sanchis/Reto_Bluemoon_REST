package com.uguinformatica.bluemoon.apirest.models.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.POST,"/api/login").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/*").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/users/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/users/*").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/users/me").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/users/*/cart-items").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/users/*/cart-items/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/*/cart-items/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/*/cart-items/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/users/*/cart-items").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/products").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/products/*").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/products/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/*").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/orders").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/orders").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/orders/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/orders/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/*").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/silver-types").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/silver-types/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/silver-types").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/silver-types/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/silver-types/*").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/trades").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/trades/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/trades").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/trades/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/trades/*").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/trades/*/tradeables").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/trades/*/tradeables/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/trades/*/tradeables").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/trades/*/tradeables/*").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/trades/*/tradeables/*").hasAuthority("ADMIN")

                        .anyRequest().hasAuthority("ADMIN")

                )
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
