package com.example.socialmedia.model;

import java.util.List;

public record PostWithComments(
    int postId,
    String username,
    String postedAgo,
    String content,
    byte[] image,
    int likeCount,
    List<Comment> comments
) {}
