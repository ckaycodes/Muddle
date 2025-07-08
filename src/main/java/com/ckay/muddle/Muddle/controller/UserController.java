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


// handles incoming requests, uses HTTP verbs (CRUD operations) + resource URLS to interact with data

@RestController
@RequestMapping("/api/users") //RESOURCE URL -> Represents users endpoint | using "api" to differentiate backend route
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {  //
        this.userService = userService;
    }

    @PostMapping("/test")
    public ResponseEntity<User> createTestUser() {    //ResponseEntity<T> is a class in Spring that represents the entire HTTP response.
        User createdUser = userService.testCreateUser();
        return ResponseEntity.ok(createdUser); // Returns 200 OK with the created user in the response body
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
