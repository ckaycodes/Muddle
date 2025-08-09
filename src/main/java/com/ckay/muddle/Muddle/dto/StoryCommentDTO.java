package com.ckay.muddle.Muddle.dto;
import com.ckay.muddle.Muddle.entity.StoryComment;

import java.time.Instant;

public class StoryCommentDTO {


    private final Long id;
    private final String body;
    private final String postedBy;
    private Instant createdAt;
    private Instant updatedAt;

    public StoryCommentDTO(StoryComment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.postedBy = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
