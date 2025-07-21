package com.ckay.muddle.Muddle.dto;

import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UserProfileDTO {

    @Getter
    private final Long id;

    @Getter
    @Size(max=200)
    private final String bio;

    @Getter
    @NotNull
    private final String equippedBadge;

    @NotNull
    private final CoffeeRoast favoriteRoast;

    public UserProfileDTO(UserProfile profile) {
        this.id = profile.getId();
        this.bio = profile.getBio();
        this.equippedBadge = profile.getEquippedBadge();
        this.favoriteRoast = CoffeeRoast.valueOf(profile.getFavoriteRoast().name());
    }

    public Object getFavoriteRoast() {
        return favoriteRoast;
    }
}
