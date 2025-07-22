package com.ckay.muddle.Muddle.dto;

import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserProfileDTO {

    private Long id;

    @Size(max=200)
    private String bio;

    private String username;

    @NotNull
    private String equippedBadge;

    @NotNull
    private CoffeeRoast favoriteRoast;

    @NotNull
    @PastOrPresent(message = "Date hired must be today or earlier")
    private LocalDate dateHired;

    @NotNull
    @PastOrPresent(message = "Date hired must be today or earlier")
    private LocalDate birthday;


    public UserProfileDTO(UserProfile profile) {
        this.id = profile.getId();
        this.username = profile.getUsername();
        this.bio = profile.getBio();
        this.equippedBadge = profile.getEquippedBadge();
        this.favoriteRoast = profile.getFavoriteRoast();
        this.dateHired = profile.getDateHired();
        this.birthday = profile.getBirthday();
    }

}
