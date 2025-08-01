package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.UserRepository;
import com.ckay.muddle.Muddle.service.StoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;
    private final UserRepository userRepository;

    public StoryController(StoryService storyService, UserRepository userRepository) {
        this.storyService = storyService;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@Valid @RequestBody Story story) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // if JWT is invalid
        if (!auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        story.setUser(user); // Set relationship here: User <--> Story

        Story createdStory = storyService.createStory(story);
        URI location = URI.create("/api/stories/" + createdStory.getId());
        return ResponseEntity
                .created(location)
                .body(new StoryDTO(createdStory, user.getId()));
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStories(@AuthenticationPrincipal UserDetails userDetails) {

        Long currentUserId = null;

        if (userDetails != null) {
            currentUserId = userRepository.findByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElse(null);
        }

        List<StoryDTO> stories = storyService.getAllStories(currentUserId);
        return ResponseEntity.ok(stories);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails // Currently authenticated user
    ) {

        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean liked = storyService.toggleLike(id, user);
        long likeCount = storyService.getLikeCount(id);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }




}
