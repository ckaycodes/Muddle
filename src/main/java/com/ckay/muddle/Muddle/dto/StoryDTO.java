package com.ckay.muddle.Muddle.dto;
import com.ckay.muddle.Muddle.entity.Story;
import lombok.Getter;

@Getter
public class StoryDTO {

    private final Long id;
    private final String title;
    private final String body;
    private final String postedBy;
    private int likeCount;
    private boolean likedByCurrentUser;

// TODO add likecoutn and likedbyCurrentUser
    public StoryDTO(Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.body = story.getBody();
        this.postedBy = story.getUser().getUsername();
    }

}
