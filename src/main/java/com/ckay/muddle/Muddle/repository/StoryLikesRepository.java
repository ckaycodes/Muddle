package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import com.ckay.muddle.Muddle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryLikesRepository extends JpaRepository<StoryLikes, Long> {

    //Has this user already liked this story?
    boolean existsByUserAndStory(User user, Story story);

    //Unlike a story
    void deleteByUserAndStory(User user, Story story);

    //Counts the number of like records for a given user and story
    long countByUserAndStory(User user, Story story);

    //How many likes does this story have?
    long countByStory(Story story);
}
