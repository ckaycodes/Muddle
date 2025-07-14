package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }


    @PostMapping
    public ResponseEntity<Story> createStory(@Valid @RequestBody Story story) {
        Story createdStory = storyService.createStory(story);
        URI location = URI.create("/api/stories/" + createdStory.getId());
        return ResponseEntity
                .created(location)
                .body(createdStory);
    }
    
    @GetMapping
    public ResponseEntity<List<Story>> getStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

}
