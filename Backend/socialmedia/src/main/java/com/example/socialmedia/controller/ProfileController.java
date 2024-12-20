package com.example.socialmedia.controller;

import com.example.socialmedia.model.Profile;
import com.example.socialmedia.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{token}")
    public ResponseEntity<Profile> getProfile(@PathVariable String token) {
        Optional<Profile> profile = profileService.fetchProfile(token);
        // Check if profile exists, and return appropriate HTTP response
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
