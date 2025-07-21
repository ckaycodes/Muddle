package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.dto.UserProfileDTO;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import com.ckay.muddle.Muddle.repository.UserProfileRepository;
import com.ckay.muddle.Muddle.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;


    public UserProfileController(UserProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    //TODO Setup get profile info to display on a MEET THE PHAM page

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserProfileDTO dto) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user); // set the relation back to user
        }

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
