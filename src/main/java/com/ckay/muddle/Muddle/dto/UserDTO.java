package com.ckay.muddle.Muddle.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
public class UserDTO {
    final private Long id;
    final private String username;
    final private String  email;

    public UserDTO(Long Id, String username, String email) {
        this.id = Id;
        this.username = username;
        this.email = email;
    }

}
