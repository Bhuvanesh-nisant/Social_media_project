package com.example.socialmedia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.model.Profile;
import com.example.socialmedia.model.ProfilePostWithComments;
import com.example.socialmedia.service.ProfilePostService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfilePostService profilePostService;

    public ProfileController(ProfilePostService profilePostService) {
        this.profilePostService = profilePostService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getUserProfileAndPosts(@PathVariable String token) {
        Optional<Profile> profile = profilePostService.fetchUserProfile(token);
        List<ProfilePostWithComments> posts = profilePostService.fetchUserPosts(token);

        Map<String, Object> response = new HashMap<>();
        response.put("profile", profile.orElse(null));
        response.put("posts", posts);

        return ResponseEntity.ok(response);
    }
}
