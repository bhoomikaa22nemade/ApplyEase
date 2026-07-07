package com.applyease.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyease.backend.dto.LoginRequest;
import com.applyease.backend.dto.RegisterRequest;
import com.applyease.backend.model.User;
import com.applyease.backend.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // ==========================
    // Register User
    // ==========================

    public Map<String, Object> register(RegisterRequest request) {

        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(request.getEmail())) {

            response.put("success", false);
            response.put("message", "Email already registered.");

            return response;

        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setExperience(request.getExperience());

        // For now we save plain password.
        // Later we'll encrypt it.

        user.setPassword(request.getPassword());

        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Registration Successful");

        return response;

    }

    // ==========================
    // Login User
    // ==========================

    public Map<String, Object> login(LoginRequest request) {

        Map<String, Object> response = new HashMap<>();

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {

            response.put("success", false);
            response.put("message", "User not found");

            return response;

        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {

            response.put("success", false);
            response.put("message", "Invalid Password");

            return response;

        }

        response.put("success", true);
        response.put("message", "Login Successful");

        response.put("username", user.getName());

        response.put("email", user.getEmail());

        return response;

    }

}
