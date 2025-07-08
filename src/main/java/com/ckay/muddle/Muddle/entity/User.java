package com.ckay.muddle.Muddle.entity;

import jakarta.persistence.*;

// Represents Data with OOP principles

@Entity
@Table(name = "users")
public class User {

    @Id // Annotation to designate the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Relies on an auto-incrementing database column
    private Long id;
    private String username;
    private String email;


    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
