package com.example.socialmedia.model;

public record PostRequest(
    String username,
    String content,
    String image,
    int likeCount,
    String token
) {}
