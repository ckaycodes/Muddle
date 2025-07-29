package com.ckay.muddle.Muddle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "story_likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "story_id"}) // Prevent duplicate likes
        }
)
public class StoryLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonBackReference(value = "user-story")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference(value = "story-likes")
    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "story_id",  nullable = false)
    private Story story;

    public StoryLikes(User user, Story story) {
        this.user = user;
        this.story = story;
    }
}
