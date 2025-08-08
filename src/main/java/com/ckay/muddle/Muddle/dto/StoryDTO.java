package com.ckay.muddle.Muddle.dto;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@Getter
public class StoryDTO {

    private final Long id;

    @NotNull
    private final String title;

    @NotNull
    private final String body;

    private final String postedBy;
    private final int likeCount;
    private final boolean likedByCurrentUser;
    private List<String> usernamesWhoLiked;
    @JsonProperty("isOwner")
    private final boolean isOwner;
    private Instant createdAt;
    private Instant updatedAt;


    public StoryDTO(Story story, Long currentUserId) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.body = story.getBody();
        this.postedBy = story.getUser().getUsername();
        this.likeCount = story.getStoryLikes().size();
        this.usernamesWhoLiked = story.getStoryLikes()
                .stream()
                .map(sl -> sl.getUser().getUsername()).toList();

        this.likedByCurrentUser = currentUserId != null &&
                story.getStoryLikes().stream()
                        .anyMatch(sl -> sl.getUser().getId().equals(currentUserId));

        this.isOwner = story.getUser().getId().equals(currentUserId);
        this.createdAt = story.getCreatedAt();
        this.updatedAt = story.getUpdatedAt();

    }



    public StoryDTO(Story story) {
        this(story, null);
    }
}
