package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.UserRepository;
import com.ckay.muddle.Muddle.service.StoryService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
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
        String username = auth.getName();

        // if JWT is invalid
        if (!auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        story.setUser(user); // Set relationship here

        Story createdStory = storyService.createStory(story);
        URI location = URI.create("/api/stories/" + createdStory.getId());
        return ResponseEntity
                .created(location)
                .body(new StoryDTO(createdStory));
    }

    
    @GetMapping
    public ResponseEntity<List<StoryDTO>> getStories() {

        List<StoryDTO> StoryDTO = storyService.getAllStories()
                .stream()
                .map(story -> new StoryDTO(story))
                .collect(Collectors.toList());
        return ResponseEntity.ok(StoryDTO);
    }

}
