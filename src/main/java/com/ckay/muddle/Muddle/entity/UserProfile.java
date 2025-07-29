package com.ckay.muddle.Muddle.entity;

import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String bio;

    //TODO make it so certain people get badges based on their time at philz (veteran)

    private String equippedBadge;

    private LocalDate dateHired;

    private LocalDate birthday;


    @Enumerated(EnumType.STRING)
    private CoffeeRoast favoriteRoast;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setFavoriteRoast(Object favoriteRoast) {
        this.favoriteRoast = (CoffeeRoast) favoriteRoast;
    }

}
