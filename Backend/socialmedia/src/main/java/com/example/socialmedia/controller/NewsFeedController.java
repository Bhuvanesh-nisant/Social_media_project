package com.example.socialmedia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.model.PostWithComments;
import com.example.socialmedia.service.NewsFeedService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/newsfeed")
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @GetMapping
    public List<PostWithComments> getNewsFeed() {
        return newsFeedService.fetchNewsFeed();
    }
}
