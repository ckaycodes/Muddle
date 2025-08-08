package com.ckay.muddle.Muddle.service;

import com.ckay.muddle.Muddle.dto.StoryDTO;
import com.ckay.muddle.Muddle.dto.UserProfileDTO;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import com.ckay.muddle.Muddle.entity.User;
import com.ckay.muddle.Muddle.entity.UserProfile;
import com.ckay.muddle.Muddle.exception.UnauthorizedException;
import com.ckay.muddle.Muddle.repository.StoryLikesRepository;
import com.ckay.muddle.Muddle.repository.StoryRepository;
import com.ckay.muddle.Muddle.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

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

    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }

    @Transactional //Seemed to fix error on retrieval (likely due to the nature of liking posts)
    public Story getById(Long id) {
        return storyRepository.findByIdWithUserAndLikes(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));
    }

    public User assertOwnership(User user, Story story) {

        if (user.getId().equals(story.getUser().getId())) {
            return user;
        }
        else {
            throw new UnauthorizedException("User is not authorized to edit story");
        }
    }

    public Story mapDtoToUserStory(StoryDTO dto, User user) {
        Long storyCreatorId = dto.getId();

        Story story = storyRepository.findById(storyCreatorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        User loggedInUser = assertOwnership(user, story);
        story.setUser(loggedInUser);

        story.setTitle(dto.getTitle());
        story.setBody(dto.getBody());

        return story;
    }



}
