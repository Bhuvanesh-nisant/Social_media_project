package com.example.socialmedia.model;


public record Post(
	    String username,
	    String postedAgo,
	    String content,
	    byte[] image,
	    int likeCount
	) {}
