package com.example.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    // Endpoint to fetch all posts
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // Endpoint to create a new post
    @PostMapping("/posts")
    public void createPost(@RequestBody Post post) {
        postService.createPost(post);
    }

    // Endpoint to fetch comments for a specific post
    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsForPost(@PathVariable int postId) {
        return postService.getCommentsForPost(postId);
    }

    // Endpoint to create a new comment for a specific post
    @PostMapping("/posts/{postId}/comments")
    public void createComment(@PathVariable int postId, @RequestBody Comment comment) {
        postService.createComment(postId, comment);
    }
}
