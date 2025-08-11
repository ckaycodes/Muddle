package com.ckay.muddle.Muddle.dto;
import com.ckay.muddle.Muddle.entity.StoryComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StoryCommentDTO {


    private Long id;
    @NotBlank(message = "Comment body is required")
    private String body;
    private String postedBy;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonProperty("isOwner")
    private final boolean isOwner;

    public StoryCommentDTO(StoryComment comment, Long currentUserId) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.postedBy = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();

        this.isOwner = comment.getUser().getId().equals(currentUserId);
    }

    //Used when no JSON object ownership field is required (used outside a REST controller)
    public StoryCommentDTO(StoryComment storyComment) {
        this(storyComment, null);
    }


}
