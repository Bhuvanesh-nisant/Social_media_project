package com.example.socialmedia.service;

import org.springframework.stereotype.Service;

import com.example.socialmedia.model.PostWithComments;
import com.example.socialmedia.repository.PostRepository;

import java.util.List;

@Service
public class NewsFeedService {
    private final PostRepository postRepository;

    public NewsFeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostWithComments> fetchNewsFeed() {
        return postRepository.getAllPostsWithComments();
    }
}
