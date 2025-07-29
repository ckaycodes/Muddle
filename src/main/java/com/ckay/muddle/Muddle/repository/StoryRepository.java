package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    // Each story will have its corresponding User fully fetched in same query (avoids lazy loading issues)
    @Query("SELECT s FROM Story s JOIN FETCH s.user")
    List<Story> findAllWithUsers();


}
