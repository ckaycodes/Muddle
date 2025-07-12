package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// handles incoming requests, uses HTTP verbs (CRUD operations) + resource URLS to interact with data

@RestController
@RequestMapping("/api/users") //RESOURCE URL -> Represents users endpoint | using "api" to differentiate backend route
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {  //
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) { //@RequestBody binds body of incoming http request
        User createdUser = userService.registerUser(user);
        URI location = URI.create("/api/users/" + createdUser.getId());
        return ResponseEntity
                .created(location) // shortcut for status(201) + location header
                .body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
