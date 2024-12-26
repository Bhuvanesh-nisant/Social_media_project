package com.example.socialmedia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.PostWithComments;
import com.example.socialmedia.service.NewsFeedService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/newsfeed")
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @GetMapping
    public ResponseEntity<List<PostWithComments>> getNewsFeed() {
        return ResponseEntity.ok(newsFeedService.fetchNewsFeed());
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Map<String, String>> likePost(@PathVariable int postId) {
        boolean isUpdated = newsFeedService.incrementLikeCount(postId);
        Map<String, String> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Like incremented successfully.");
        } else {
            response.put("message", "Error incrementing like.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addComment(@PathVariable int postId, @RequestBody Comment newComment) {
        newsFeedService.addCommentToPost(postId, newComment);
        return ResponseEntity.ok("Comment added successfully.");
    }
}
