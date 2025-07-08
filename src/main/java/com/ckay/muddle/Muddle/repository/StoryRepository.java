package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
    // You can add custom queries here if needed later
}
