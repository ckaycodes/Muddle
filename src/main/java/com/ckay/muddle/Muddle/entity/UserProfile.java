package com.ckay.muddle.Muddle.entity;

import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String bio;

    @Setter
    private String equippedBadge; // TODO: set up badge selection from a menu

    @Enumerated(EnumType.STRING)
    private CoffeeRoast favoriteRoast;

    @Setter
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setFavoriteRoast(Object favoriteRoast) {
        this.favoriteRoast = (CoffeeRoast) favoriteRoast;
    }

}
