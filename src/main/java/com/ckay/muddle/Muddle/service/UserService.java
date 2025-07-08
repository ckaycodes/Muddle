package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// ---- where business logic lives (like “calculate total price”) ----

@Service
public class UserService {

    // Constructor Injection
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User testCreateUser() {
        User user = new User("chandler", "chandler@example.com");
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
