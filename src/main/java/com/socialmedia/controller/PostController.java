package com.socialmedia.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

   /* @PostMapping("/create")
    public ResponseEntity<Post> createPost(
        @RequestBody Map<String, String> payload,
        @RequestHeader("X-User-Id") Integer userId) {

        String content = payload.get("content");
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            Post post = new Post();
            post.setContent(content);
            post.setUser(userOpt.get());
            return ResponseEntity.ok(postRepo.save(post));
        }
        return ResponseEntity.badRequest().build();
    }
*/
    @PostMapping("/create")
    public ResponseEntity<String> createPost(
            @RequestParam("content") String content,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        User user = userRepo.findById(userId).orElseThrow();
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        if (image != null && !image.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get("uploads", filename);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
            post.setImageUrl("/uploads/" + filename);
        }

        postRepo.save(post);
        return ResponseEntity.ok("Post saved");
    }


    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postRepo.findAll());
    }
    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody Map<String, Object> data) {
        Integer postId = Integer.valueOf(data.get("postId").toString());
        Integer userId = Integer.valueOf(data.get("userId").toString());

        Post post = postRepo.findById(postId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        if (!post.getLikedBy().contains(user)) {
            post.getLikedBy().add(user);
            postRepo.save(post); // 💾 saves the like permanently
        }
        return ResponseEntity.ok("Post liked");
    }
    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable Integer userId) {
        return postRepo.findByUserId(userId);
    }


    /*@DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        Optional<Post> postOpt = postRepo.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUser().getId().equals(userId)) {
                postRepo.delete(post);
                return ResponseEntity.ok("Post deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own posts");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
    }*/

}
