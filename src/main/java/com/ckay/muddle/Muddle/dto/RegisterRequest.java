package com.ckay.muddle.Muddle.dto;

import lombok.Getter;
import lombok.Setter;

// DTO for User register requests

@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
