package com.ckay.muddle.Muddle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//TODO Add Lombok

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    // Getters and setters
    @Id // Annotation to designate the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Relies on an auto-incrementing database column
    private Long id;
    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;


}
