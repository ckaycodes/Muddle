package com.ckay.muddle.Muddle.dto;
import com.ckay.muddle.Muddle.entity.Story;
import com.ckay.muddle.Muddle.entity.StoryLikes;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoryDTO {

    private final Long id;
    private final String title;
    private final String body;
    private final String postedBy;
    private final int likeCount;
    private final boolean likedByCurrentUser;
    private List<Long> userIdsWhoLiked;

// TODO add likecoutn and likedbyCurrentUser

    public StoryDTO(Story story, Long currentUserId) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.body = story.getBody();
        this.postedBy = story.getUser().getUsername();
        this.likeCount = story.getStoryLikes().size();
        this.userIdsWhoLiked = story.getStoryLikes()
                .stream()
                .map(sl -> sl.getUser().getId()).toList();
        this.likedByCurrentUser = currentUserId != null && userIdsWhoLiked.contains(currentUserId);


    }

    public StoryDTO(Story story) {
        this(story, null);
    }

}
