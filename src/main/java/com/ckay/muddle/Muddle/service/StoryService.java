package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoryService {

    // Constructor Injection
    private final StoryRepository storyRepository;
    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public Story createStory(Story story) {
        return storyRepository.save(story);
    }

    public List<Story> getAllStories() { return storyRepository.findAllWithUsers(); }

}
