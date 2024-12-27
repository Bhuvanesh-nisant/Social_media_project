package com.example.socialmedia.model;

public record PostUpload(
    String username,
    String content,
    String image, // Base64 image value
    String token
) {}
