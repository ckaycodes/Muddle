package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// CONTROLLER ---> Routes that handle HTTP requests.

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/test")
    public ResponseEntity<User> createTestUser() {
        User createdUser = userService.testCreateUser();
        return ResponseEntity.ok(createdUser); // sending back a 200 OK HTTP response with the created
                                               // user in the response body
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
