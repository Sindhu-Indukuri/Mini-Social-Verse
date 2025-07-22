package com.socialmedia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String profilePicUrl;
    
    private String role = "USER"; // USER or ADMIN

    private boolean blocked = false;

    @ManyToMany
    @JoinTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<User> followers = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_following",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    // Optionally uncomment if needed
    // @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<User> following = new HashSet<>();
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.blocked = blocked;
    }

}