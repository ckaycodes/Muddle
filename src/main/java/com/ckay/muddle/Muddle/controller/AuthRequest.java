package com.ckay.muddle.Muddle.controller; // or your chosen package

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {

    // Getters and setters
    private String username;
    private String password;

    // Default constructor (needed for JSON deserialization)
    public AuthRequest() {}

}
