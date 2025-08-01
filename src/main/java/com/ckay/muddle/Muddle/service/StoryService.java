package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.repository.StoryLikesRepository;
import com.ckay.muddle.Muddle.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoryService {

    // Constructor Injection
    private final StoryRepository storyRepository;
    private final StoryLikesRepository storyLikesRepository;
    public StoryService(StoryRepository storyRepository, StoryLikesRepository storyLikesRepository) {
        this.storyRepository = storyRepository;
        this.storyLikesRepository = storyLikesRepository;
    }

    public Story createStory(Story story) {
        return storyRepository.save(story);
    }

    public List<StoryDTO> getAllStories(Long currentUserId) {
        return storyRepository.findAllWithUsersAndLikes()
                .stream()
                .map(story -> new StoryDTO(story, currentUserId))
                .toList();
    }

    @Transactional //Any data modifying operation needs to have this annotation
    public boolean toggleLike(Long storyId, User user) {

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story with ID " + storyId + " does not exist"));

        boolean alreadyLiked = storyLikesRepository.existsByUserAndStory(user, story);

        if (alreadyLiked) {
            storyLikesRepository.deleteByUserAndStory(user, story);
            return false; // now unliked
        } else {
            storyLikesRepository.save(new StoryLikes(user, story));
            return true; // now liked
        }
    }
    
    public long getLikeCount(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        return storyLikesRepository.countByStory(story);
    }



}
