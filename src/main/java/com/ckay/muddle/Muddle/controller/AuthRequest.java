package com.ckay.muddle.Muddle.controller; // or your chosen package

import lombok.Getter;
import lombok.Setter;

// (DTO, move out of Controller)
@Setter
@Getter
public class AuthRequest {

    private String username;
    private String password;


    public AuthRequest() {}

}
