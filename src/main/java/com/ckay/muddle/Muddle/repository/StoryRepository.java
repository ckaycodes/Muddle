package com.ckay.muddle.Muddle.repository;

import com.ckay.muddle.Muddle.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    // Each story will have its corresponding User fully fetched in same query (avoids lazy loading issues)
    @Query("SELECT s FROM Story s JOIN FETCH s.user")
    List<Story> findAllWithUsers();


    @Query("""
    SELECT DISTINCT s FROM Story s
    LEFT JOIN FETCH s.user
    LEFT JOIN FETCH s.storyLikes sl
    LEFT JOIN FETCH sl.user
    """)
    List<Story> findAllWithUsersAndLikes();


    /*
         Story (s)
         ├── User (s.user)
         ├── StoryLike (sl) ──► User (sl.user)
         └── StoryComment (c) ──► User (c.user)

        The User is fetched eagerly with each story/storyLike/storyComment
    */
    @Query("""
    SELECT DISTINCT s FROM Story s
    LEFT JOIN FETCH s.user
    LEFT JOIN FETCH s.storyLikes sl
    LEFT JOIN FETCH sl.user
    LEFT JOIN FETCH s.storyComments c
    LEFT JOIN FETCH c.user
    WHERE s.id = :id
    """)
    Optional<Story> findByIdWithUserAndLikesAndComments(@Param("id") Long id);


    /* TODO:
        * comments could get huge, might want a separate findCommentsByStoryId()
        * query with pagination instead of pulling them inline.
    */

}
