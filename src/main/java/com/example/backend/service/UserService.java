package com.example.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {

        this.userRepository =
                userRepository;
    }

    public User register(
            User user) {

        return userRepository.save(
                user
        );
    }

    public Optional<User> login(
            String email,
            String password) {

        return userRepository
                .findByEmail(email)
                .filter(user ->
                        user.getPassword()
                                .equals(password));
    }
}