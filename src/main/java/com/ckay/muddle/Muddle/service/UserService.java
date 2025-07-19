package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.dto.RegisterRequest;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    // Repository Constructor Injection
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(RegisterRequest registerRequest) {

        //Prevents case-sensitive usernames
        String normalizedUsername = registerRequest.getUsername().toLowerCase();

        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new IllegalArgumentException("Username already taken.");
        }

        User newUser = new User();
        newUser.setUsername(normalizedUsername);
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
