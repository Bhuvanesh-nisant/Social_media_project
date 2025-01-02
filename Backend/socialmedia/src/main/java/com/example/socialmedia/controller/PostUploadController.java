package com.example.socialmedia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.model.PostUpload;
import com.example.socialmedia.service.PostUploadService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostUploadController {
    private final PostUploadService postUploadService;

    public PostUploadController(PostUploadService postUploadService) {
        this.postUploadService = postUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPost(@RequestBody PostUpload post) {
        boolean isUploaded = postUploadService.uploadPost(post);
        if (isUploaded) {
            return ResponseEntity.ok("Post uploaded successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to upload post.");
        }
    }
}
