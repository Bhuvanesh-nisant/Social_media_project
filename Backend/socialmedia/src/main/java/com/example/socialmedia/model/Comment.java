package com.example.socialmedia.model;

public record Comment(
	    int postId,
	    String content,
	    String timestamp
	) {}
