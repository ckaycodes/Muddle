package com.ckay.muddle.Muddle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be under 100 characters")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;


    // Each story belongs to exactly one user, but a user can have many stories
    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // only fetches user object when needed
    @JoinColumn(name = "user_id") // Store foreign key user_id, linking each story to a user
    private User user;

}
