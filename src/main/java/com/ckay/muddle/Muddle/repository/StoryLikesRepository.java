package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import com.ckay.muddle.Muddle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryLikesRepository extends JpaRepository<StoryLikes, Long> {

    boolean existsByUserAndStory(User user, Story story);
    void deleteByUserAndStory(User user, Story story);
    long countByUserAndStory(User user, Story story);


    long countByStory(Story story);
}
