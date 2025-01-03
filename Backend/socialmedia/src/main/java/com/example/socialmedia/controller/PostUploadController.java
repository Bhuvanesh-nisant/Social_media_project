package com.example.socialmedia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, String>> uploadPost(@RequestBody PostUpload post) {
        boolean isUploaded = postUploadService.uploadPost(post);
        Map<String, String> response = new HashMap<>();
        if (isUploaded) {
            response.put("message", "Post uploaded successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to upload post.");
            return ResponseEntity.status(500).body(response);
        }
    }
}

