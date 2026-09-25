package com.product.api.controller;

import com.product.api.dto.in.LoginRequest;
import com.product.api.entity.User;
import com.product.config.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@Tag(name = "Authentication", description = "Endpoints para la gestión de autenticación.")
public class CtrlAuth {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        User user   = (User) auth.getPrincipal();
        String token = jwtUtil.generateToken(user);

        return Map.of("token", token);
    }
}