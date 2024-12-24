package com.example.socialmedia.model;

public record UpdateImageRequest(
    String token,
    String profilePhoto,
    String coverPhoto
) {}
