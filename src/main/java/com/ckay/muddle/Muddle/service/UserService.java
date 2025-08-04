package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.dto.RegisterRequest;
import com.ckay.muddle.Muddle.dto.UserProfileDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    // Repository Constructor Injection
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(RegisterRequest registerRequest) {

        //Prevents case-sensitive usernames & emails
        String normalizedUsername = registerRequest.getUsername().toLowerCase();
        String normalizedEmail = registerRequest.getEmail().toLowerCase();

        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new IllegalArgumentException("Username already taken.");
        } else if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("Email already taken.");
        }

        User newUser = new User();
        newUser.setUsername(normalizedUsername);
        newUser.setEmail(normalizedEmail);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserProfile mapDtoToUserProfile(UserProfileDTO dto, User user) {
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



}
