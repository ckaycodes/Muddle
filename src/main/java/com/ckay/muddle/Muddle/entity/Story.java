package com.ckay.muddle.Muddle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be under 100 characters")
    private String title;

    // TODO Add body limit
    @NotBlank(message = "Body is required")
    @Column(columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false)
    private Instant updatedAt;

    /*
     * Each story belongs to exactly one user, but a user can have many stories
     * only fetches user object when needed (lazy fetch type) to avoid circular reference
     * Store foreign key user_id, linking each story to a user
     */
    @JsonBackReference(value = "user-story")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //explicit link of foreign key
    private User user;

    /*
    * Changed storyLikes to a Set to resolve multiple bag fetch error in repository query
    * as likes don't require a specific order upon retrieval.
    */
    @JsonManagedReference(value = "story-likes")
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoryLikes> storyLikes = new HashSet<>();

    @JsonManagedReference(value = "story-comments")
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryComment> storyComments = new ArrayList<>();



}
