package com.example.backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(
            UserService userService,
            UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(
            @RequestBody User user) {

        return userService.register(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody User request) {

        Optional<User> user =
                userService.login(
                        request.getEmail(),
                        request.getPassword());

        if (user.isEmpty()) {
            throw new RuntimeException(
                    "Invalid Credentials");
        }

        Map<String, String> response =
                new HashMap<>();

        response.put(
                "token",
                "dummy-jwt-token");

        response.put(
                "name",
                user.get().getName());

        response.put(
                "email",
                user.get().getEmail());

        return response;
    }

    @GetMapping("/profile/{email}")
    public User getProfile(
            @PathVariable String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"));
    }
}