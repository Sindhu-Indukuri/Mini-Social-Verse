package com.socialmedia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    
    private String imageUrl;

    @ManyToOne
    private User user;

    @ManyToMany
    //@com.fasterxml.jackson.annotation.JsonIgnore // 🔥 add this
    private Set<User> likedBy = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore // 🔥 add this
    private List<Comment> comments = new ArrayList<>();
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}





