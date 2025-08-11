package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryCommentRepository extends JpaRepository<StoryComment, Long> {
    
    Long getStoryCommentById(Long id);
}
