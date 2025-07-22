package com.socialmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBlocked(true);
        userRepository.save(user);

        return ResponseEntity.ok("User blocked successfully");
    }

    @PostMapping("/unblock/{userId}")
    public ResponseEntity<String> unblockUser(@PathVariable Integer userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBlocked(false);
        userRepository.save(user);

        return ResponseEntity.ok("User unblocked successfully");
    }
}
