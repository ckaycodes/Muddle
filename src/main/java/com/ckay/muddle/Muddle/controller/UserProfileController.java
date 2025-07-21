package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.dto.UserProfileDTO;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import com.ckay.muddle.Muddle.repository.UserProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileRepository profileRepository;

    public UserProfileController(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserProfileDTO dto) {

        UserProfile profile = user.getProfile();

        profile.setBio(dto.getBio());
        profile.setEquippedBadge(dto.getEquippedBadge());
        profile.setFavoriteRoast(dto.getFavoriteRoast());


        UserProfile updated = profileRepository.save(profile);
        return ResponseEntity.ok(new UserProfileDTO(updated));
    }


    @GetMapping("/coffee-roasts")
    public ResponseEntity<List<Map<String, String>>> getCoffeeRoasts() {
        List<Map<String, String>> roasts = Arrays.stream(CoffeeRoast.values())
                .map(roast -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", roast.name());
                    map.put("displayName", roast.getDisplayName());
                    map.put("hexColor", roast.getHexColor());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(roasts);
    }

}
