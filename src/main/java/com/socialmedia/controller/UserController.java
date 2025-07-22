package com.socialmedia.controller;

import com.socialmedia.DTO.UserDTO;
import com.socialmedia.model.*;
import com.socialmedia.repository.UserRepository;
import com.socialmedia.service.UserService;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    
    
    @Autowired
    private UserRepository userRepo;

  
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "User already registered with this email"));
        }

        userRepo.save(user);
        return ResponseEntity.ok(user);
    }


 /*   @PostMapping("/login")
    public User login(@RequestBody Map<String,String>body) {
        String email = body.get("email");
        String password = body.get("password");
        return userService.login(email, password).orElse(null);
    }*/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userService.login(email, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.isBlocked()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is blocked");
            }
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getById(id).orElse(null);
    }
    @PostMapping("/upload-profile")
    public ResponseEntity<String> uploadProfile(@RequestParam("file") MultipartFile file, @RequestParam Integer userId) throws IOException, java.io.IOException {
        User user = userRepo.findById(userId).orElseThrow();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        user.setProfilePicUrl("/uploads/" + fileName); // relative URL
        userRepo.save(user);

        return ResponseEntity.ok("/uploads/" + fileName);
    }
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                       .map(UserDTO::new)
                       .collect(Collectors.toList());
    }
   @PostMapping("/users/{currentUserId}/follow/{targetUserId}")
    public ResponseEntity<?> followUser(@PathVariable Integer currentUserId, @PathVariable Integer targetUserId) {
        userService.followUser(currentUserId, targetUserId);
        
        return ResponseEntity.ok("Followed successfully");
    }

    @GetMapping("/users/{id}/following")
    public List<UserDTO> getFollowing(@PathVariable Integer id) {
        User user = userRepo.findById(id).orElseThrow();
        return user.getFollowing().stream()
                   .map(UserDTO::new)
                   .collect(Collectors.toList());
    }
    @PostMapping("/users/{currentUserId}/unfollow/{targetUserId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Integer currentUserId, @PathVariable Integer targetUserId) {
        userService.unfollowUser(currentUserId, targetUserId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

   /* @PostMapping("/users/{currentUserId}/follow/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable Integer currentUserId, @PathVariable Integer targetUserId) {
        userService.followUser(currentUserId, targetUserId);
        return ResponseEntity.ok("Followed successfully");
    }
*/


    /*@GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                       .map(UserDTO::new)
                       .collect(Collectors.toList());
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new UserDTO(user)); // Don't expose password etc.
    }

    @PostMapping("/{userId}/toggle-follow/{followId}")
    public ResponseEntity<?> toggleFollow(@PathVariable Integer userId, @PathVariable Integer followId) {
        userService.toggleFollow(userId, followId);
        return ResponseEntity.ok().build();
    }
*/


    
    
   

}