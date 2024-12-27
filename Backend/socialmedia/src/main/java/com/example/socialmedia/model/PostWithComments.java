package com.example.socialmedia.model;

import java.util.List;

public record PostWithComments(
    int postId,
    String username,
    String postedAgo,
    String content,
    String image,  // String type for image (Base64 or raw value)
    int likeCount,
    List<Comment> comments
) {}
