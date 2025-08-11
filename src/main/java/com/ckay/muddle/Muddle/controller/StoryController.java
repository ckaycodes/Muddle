package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.dto.StoryCommentDTO;
import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryComment;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.StoryCommentRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Comparator;
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
    private final StoryCommentRepository storyCommentRepository;

    public StoryController(StoryService storyService, UserRepository userRepository, StoryRepository storyRepository, CustomUserDetailsService customUserDetailsService, StoryCommentRepository storyCommentRepository) {
        this.storyService = storyService;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.storyCommentRepository = storyCommentRepository;
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

        // TODO -- Configure UserDetailsService to return ID directly when loading the user instead (?)
        if (userDetails != null) {
            currentUserId = userRepository.findByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElse(null);
        }

        // Using UserDetails class to apply visibility rules & user likes
        List<StoryDTO> stories = storyService.getAllStories(currentUserId);
        return ResponseEntity.ok(stories);
    }

    // Includes current user ID so the method applies ownership
    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryByID(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        Long currentUserId = null;
        if (userDetails != null) {
            currentUserId = userRepository.findByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElse(null);
        }

        Story story = storyService.getStoryByIdWithDetails(id);
        return ResponseEntity.ok(new StoryDTO(story, currentUserId));
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

    //ResponseEntity of an unbounded wildcard to represent possible null value (after deletion)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStory(@PathVariable Long id, Authentication authentication) {

        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        storyService.assertOwnership(user, story);

        storyService.deleteStory(id);
        return ResponseEntity.noContent().build(); //HTTP 204 (resource gone)
    }

    @PostMapping("/{storyId}/comments")
    public ResponseEntity<StoryCommentDTO> createComment(@PathVariable Long storyId,
                                                         @Valid @RequestBody StoryCommentDTO commentDTO,
                                                         Authentication authentication) {
        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        Story story = storyService.getStoryById(storyId);
        if (story == null) {
            return ResponseEntity.notFound().build();
        }

        StoryComment storyComment = new StoryComment();
        storyComment.setStory(story);
        storyComment.setUser(user);
        storyComment.setBody(commentDTO.getBody());

        StoryComment createdComment = storyService.createStoryComment(storyComment);

        URI location = URI.create("/api/stories/" + story.getId() + "/comments/" + createdComment.getId());
        return ResponseEntity.created(location).body(new StoryCommentDTO(createdComment,user.getId()));
    }

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<StoryCommentDTO>> getStoryComments(@PathVariable Long storyId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Load story with comments eagerly fetched
        Story story = storyService.getStoryByIdWithDetails(storyId);
        if (story == null) {
            return ResponseEntity.notFound().build();
        }

        Long currentUserId = (userDetails != null)
                ? userRepository.findByUsername(userDetails.getUsername())
                .map(User::getId) // If a user is found, return their id ( user -> user.getId() )
                .orElse(null)
                : null; // No user found

        List<StoryCommentDTO> commentDTOList = story.getStoryComments()
                .stream()
                .sorted(Comparator.comparing(StoryComment::getCreatedAt)) //sort comments
                .map(comment -> new StoryCommentDTO(comment, currentUserId))
                .toList();

        return ResponseEntity.ok(commentDTOList);
    }

    @DeleteMapping("/{storyId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long storyId,
                                            @PathVariable Long commentId,
                                            Authentication authentication) {
        User user = customUserDetailsService.getAuthenticatedUser(authentication);

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        StoryComment comment = storyCommentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        storyService.checkCommentAuth(story,comment,user);

        storyService.deleteComment(comment);

        return ResponseEntity.noContent().build();
    }

}
