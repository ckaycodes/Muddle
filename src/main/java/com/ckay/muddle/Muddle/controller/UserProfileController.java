package com.ckay.muddle.Muddle.controller;

import com.ckay.muddle.Muddle.dto.UserProfileDTO;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.enums.CoffeeRoast;
import com.ckay.muddle.Muddle.repository.UserProfileRepository;
import com.ckay.muddle.Muddle.repository.UserRepository;
import com.ckay.muddle.Muddle.service.CustomUserDetailsService;
import com.ckay.muddle.Muddle.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;


    public UserProfileController(UserProfileRepository profileRepository,
                                 UserRepository userRepository, UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Transactional
    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            Authentication authentication,  //Currently Authenticated User
            @Valid @RequestBody UserProfileDTO dto) {

        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        // Update the UserProfile with passed dto
        UserProfile profile = userService.mapDtoToUserProfile(dto, user);

        UserProfile updated = profileRepository.save(profile);

        // Return the updated UserProfile object in a DTO
        return ResponseEntity.ok(new UserProfileDTO(updated));
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
                .collect(Collectors.toList());

        return ResponseEntity.ok(roasts); //return all roasts and their data
    }

}
