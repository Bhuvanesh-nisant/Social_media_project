package com.example.socialmedia.model;

import java.util.List;

public record ProfilePostWithComments(
    int postId,
    String username,
    String postedAgo,
    String content,
    String image,  // String type for image
    int likeCount,
    List<Comment> comments
) {}