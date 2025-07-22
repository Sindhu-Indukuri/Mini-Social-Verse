package com.socialmedia.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.socialmedia.model.Comment;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;
    
    @PostMapping("/add/{postId}")
    public ResponseEntity<String> addComment(
            @PathVariable Integer postId,
            @RequestBody Map<String, String> payload,
            HttpServletRequest request) {

        Optional<Post> postOpt = postRepo.findById(postId);
        if (postOpt.isEmpty()) return ResponseEntity.badRequest().body("Invalid post ID");

        // ✅ Assume user is stored in session or extract user from token/local storage
        String userIdStr = request.getHeader("X-User-Id"); // or use a session or token
        if (userIdStr == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");

        Integer userId = Integer.parseInt(userIdStr);
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");

        String content = payload.get("content");

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(postOpt.get());
        comment.setUser(userOpt.get());

        commentRepo.save(comment);
        return ResponseEntity.ok("Comment saved");
    }
    @PostMapping("/like")
    public ResponseEntity<String> likeComment(@RequestBody Map<String, Object> data) {
        Integer commentId = Integer.valueOf(data.get("commentId").toString());
        Integer userId = Integer.valueOf(data.get("userId").toString());

        Comment comment = commentRepo.findById(commentId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        comment.getLikedBy().add(user);
        commentRepo.save(comment);
        return ResponseEntity.ok("Comment liked");
    }



    @GetMapping("/post/{postId}")
    public List<Comment> getComments(@PathVariable Integer postId) {
        return commentRepo.findAll()
                          .stream()
                          .filter(c -> c.getPost().getId().equals(postId))
                          .toList();
    }
    @GetMapping("/comments/my-posts/{userId}")
    public List<Comment> getCommentsOnMyPosts(@PathVariable Integer userId) {
        User user = userRepo.findById(userId).orElseThrow();

        List<Post> postsByUser = postRepo.findByUser(user);

        List<Comment> allComments = new ArrayList<>();
        for (Post post : postsByUser) {
            allComments.addAll(post.getComments());
        }

        return allComments;
    }

}
