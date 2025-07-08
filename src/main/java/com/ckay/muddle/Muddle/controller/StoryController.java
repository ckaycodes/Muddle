package com.ckay.muddle.Muddle.controller;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.service.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/test")
    public ResponseEntity<Story> createTestStory() {
        Story createdStory = storyService.testCreateStory();
        return ResponseEntity.ok(createdStory);
    }

    @GetMapping
    public ResponseEntity<List<Story>> getStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

}
