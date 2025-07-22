package com.socialmedia.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data   // <-- VERY IMPORTANT (generates getters/setters)
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @ManyToMany
    private Set<User> likedBy = new HashSet<>(); 
    
    private String content;

}
