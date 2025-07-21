package com.ckay.muddle.Muddle.dto;

import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfileDTO {


    private Long id;

    @Size(max=200)
    private String bio;

    @NotNull
    private String equippedBadge;

    @NotNull
    private CoffeeRoast favoriteRoast;

    public UserProfileDTO(UserProfile profile) {
        this.id = profile.getId();
        this.bio = profile.getBio();
        this.equippedBadge = profile.getEquippedBadge();
        this.favoriteRoast = profile.getFavoriteRoast();
    }

}
