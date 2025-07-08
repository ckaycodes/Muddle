package com.ckay.muddle.Muddle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
public class Story {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String postedBy;


    public Story(String title, String body, String postedBy) {
        this.title = title;
        this.body = body;
        this.postedBy = postedBy;
    }

    public Story() {}

}
