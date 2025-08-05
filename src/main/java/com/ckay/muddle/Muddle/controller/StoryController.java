package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.StoryRepository;
import com.ckay.muddle.Muddle.repository.UserRepository;
import com.ckay.muddle.Muddle.service.CustomUserDetailsService;
import com.ckay.muddle.Muddle.service.StoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;


// TODO -- Add timestamp with @CreationTimestamp/@UpdateTimestamp annotation

// TODO -- Add comments & Polls

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public StoryController(StoryService storyService, UserRepository userRepository, StoryRepository storyRepository, CustomUserDetailsService customUserDetailsService) {
        this.storyService = storyService;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
        this.customUserDetailsService = customUserDetailsService;
    }


    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@Valid @RequestBody Story story) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = customUserDetailsService.getAuthenticatedUser(auth);

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

    // Include current user ID so the method computes isOwner
    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryByID(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        Long currentUserId = null;
        if (userDetails != null) {
            currentUserId = userRepository.findByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElse(null);
        }

        Story story = storyService.getById(id);
        return ResponseEntity.ok(new StoryDTO(story,currentUserId));
    }


    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long id,
            Authentication authentication // Currently authenticated user
    ) {

        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        boolean liked = storyService.toggleLike(id, user);
        long likeCount = storyService.getLikeCount(id);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", liked);
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    // TODO: Make it so the edit and delete button remain on screen even after editing.

        @Transactional
        @PutMapping
        public ResponseEntity<StoryDTO> updateStory(
                Authentication authentication,
                @Valid @RequestBody StoryDTO dto) {

            User user = customUserDetailsService.getAuthenticatedUser(authentication);

            Story story = storyService.mapDtoToUserStory(dto, user);
            Story updatedStory = storyRepository.save(story);

            return ResponseEntity.ok(new StoryDTO(updatedStory));

        }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStory(@PathVariable Long id, Authentication authentication) {

        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        storyService.assertOwnership(user, story);

        storyService.deleteStory(id);
        return ResponseEntity.noContent().build(); //HTTP 204 (resource gone)
    }

}
