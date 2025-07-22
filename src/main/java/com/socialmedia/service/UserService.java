package com.socialmedia.service;

import com.socialmedia.DTO.UserDTO;
import com.socialmedia.model.*;
import com.socialmedia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;
    
    	public User register(User user) {
    	    User savedUser = userRepo.save(user); // First save the user
    	    emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getUsername()); // Then send email
    	    return savedUser;
    	}

    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> getById(Integer id) {
        return userRepo.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
   
    @Transactional
    public void followUser(Integer currentUserId, Integer targetUserId) {
        if (currentUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        User currentUser = userRepo.findById(currentUserId).orElseThrow(() -> new RuntimeException("Current user not found"));
        User targetUser = userRepo.findById(targetUserId).orElseThrow(() -> new RuntimeException("Target user not found"));

        // Prevent duplicate follow
        if (!currentUser.getFollowing().contains(targetUser)) {
            currentUser.getFollowing().add(targetUser);
            userRepo.save(currentUser);
        }
    }
    public void unfollowUser(Integer currentUserId, Integer targetUserId) {
        User currentUser = userRepo.findById(currentUserId).orElseThrow();
        User targetUser = userRepo.findById(targetUserId).orElseThrow();
        currentUser.getFollowing().remove(targetUser);
        userRepo.save(currentUser);
    }

   /* public void followUser(Integer currentUserId, Integer targetUserId) {
        User currentUser = userRepo.findById(currentUserId).orElseThrow();
        User targetUser = userRepo.findById(targetUserId).orElseThrow();
        currentUser.getFollowing().add(targetUser);
        userRepo.save(currentUser);
    }*/

    public List<UserDTO> getFollowing(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return user.getFollowing().stream().map(UserDTO::new).collect(Collectors.toList());
    }

  /*  public void toggleFollow(Integer userId, Integer followId) {
        User user = userRepo.findById(userId).orElseThrow();
        User followUser = userRepo.findById(followId).orElseThrow();

        if (user.getFriends().contains(followUser)) {
            user.getFriends().remove(followUser);
        } else {
            user.getFriends().add(followUser);
        }

        userRepo.save(user);
    }*/

}
