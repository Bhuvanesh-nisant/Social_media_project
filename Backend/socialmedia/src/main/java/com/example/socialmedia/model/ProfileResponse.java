package com.example.socialmedia.model;

import java.util.List;

public record ProfileResponse(
    Profile profile,
    List<PostWithComments> posts
) {}
