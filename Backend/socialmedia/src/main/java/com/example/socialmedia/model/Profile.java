package com.example.socialmedia.model;

public record Profile(
    String bio,
    String profilePhoto,
    String coverPhoto,
    String name
) { public Profile(String name) {
    this(null, null, null, name);}
}
