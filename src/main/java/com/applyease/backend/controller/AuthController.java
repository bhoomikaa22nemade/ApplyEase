package com.applyease.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.applyease.backend.dto.LoginRequest;
import com.applyease.backend.dto.RegisterRequest;
import com.applyease.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")

@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ==========================
    // Register
    // ==========================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        Map<String, Object> response =
                authService.register(request);

        if ((Boolean) response.get("success")) {

            return ResponseEntity.ok(response);

        }

        return ResponseEntity.badRequest().body(response);

    }

    // ==========================
    // Login
    // ==========================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        Map<String, Object> response =
                authService.login(request);

        if ((Boolean) response.get("success")) {

            return ResponseEntity.ok(response);

        }

        return ResponseEntity.badRequest().body(response);

    }

}
