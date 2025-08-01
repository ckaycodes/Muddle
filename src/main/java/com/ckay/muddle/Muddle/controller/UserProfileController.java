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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    @Transactional
    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            Authentication authentication,  //Currently Authenticated User
            @Valid @RequestBody UserProfileDTO dto) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Principal = object -> get User (Currently logged in)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserProfile profile = mapDtoToUserProfile(dto, user);

        //Save User with updated UserProfile
        UserProfile updated = profileRepository.save(profile);
        return ResponseEntity.ok(new UserProfileDTO(updated));
    }

    private static UserProfile mapDtoToUserProfile(UserProfileDTO dto, User user) {
        UserProfile profile = user.getProfile();
        if (profile == null) { // If User hasn't created a profile page
            profile = new UserProfile();
            profile.setUser(user); // set the relation back to user
        }

        profile.setUsername(user.getUsername());
        profile.setBio(dto.getBio());
        profile.setEquippedBadge(dto.getEquippedBadge());
        profile.setFavoriteRoast(dto.getFavoriteRoast());
        profile.setDateHired(dto.getDateHired());
        profile.setBirthday(dto.getBirthday());
        return profile;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllProfiles() {
        List<UserProfileDTO> profiles = profileRepository.findAll()
                .stream()
                .map(UserProfileDTO::new) // call this with every element in stream
                .collect(Collectors.toList());
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getProfileById(@PathVariable Long id) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
        return ResponseEntity.ok(new UserProfileDTO(profile));
    }


    @GetMapping("/coffee-roasts")
    public ResponseEntity<List<Map<String, String>>> getCoffeeRoasts() {
        List<Map<String, String>> roasts = Arrays.stream(CoffeeRoast.values())
                .map(roast -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", roast.name());
                    map.put("displayName", roast.getDisplayName());
                    map.put("hexColor", roast.getHexColor());
                    return map; //Return hashmap containing a Map of all CoffeeRoast's (enums) associated name/display name/color
                })
                .collect(Collectors.toList()); //gather into list

        return ResponseEntity.ok(roasts); //return all roasts and their data
    }

}
