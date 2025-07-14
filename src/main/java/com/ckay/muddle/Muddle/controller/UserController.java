package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.dto.RegisterRequest;
import com.ckay.muddle.Muddle.dto.UserDTO;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// handles incoming requests, uses HTTP verbs (CRUD operations) + resource URLS to interact with data

@RestController
@RequestMapping("/api/users") //RESOURCE URL -> Represents users endpoint | using "api" to differentiate backend route
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {  //
        this.userService = userService;
    }


    @PostMapping("/auth/reigster")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        userService.registerNewUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers()
                .stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }


}
