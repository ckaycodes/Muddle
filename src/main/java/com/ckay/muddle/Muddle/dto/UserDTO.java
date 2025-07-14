package com.ckay.muddle.Muddle.dto;

import com.ckay.muddle.Muddle.entity.User;
import lombok.Getter;

@Getter
public class UserDTO {
    final private String username;
    final private String  email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
