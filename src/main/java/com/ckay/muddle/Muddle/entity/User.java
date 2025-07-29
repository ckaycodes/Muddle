package com.ckay.muddle.Muddle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


//TODO Add Lombok

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "stories")
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

    // One User can have many Stories, changes to User will cascade to Story list,
    // if a Story is removed from the list it should also be removed from database.
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;



}
