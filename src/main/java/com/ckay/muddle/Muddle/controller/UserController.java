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



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {  //
        this.userService = userService;
    }


    // TODO -- Utilize ModelMapper or MapStruct dependencies later for DTOs --

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers()
                .stream()
                .map(user -> new UserDTO(user.getId(),user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }


}
