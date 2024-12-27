package com.example.socialmedia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.socialmedia.model.Profile;
import com.example.socialmedia.model.ProfilePostWithComments;
import com.example.socialmedia.repository.ProfilePostRepository;

@Service
public class ProfilePostService {
    private final ProfilePostRepository profilePostRepository;

    public ProfilePostService(ProfilePostRepository profilePostRepository) {
        this.profilePostRepository = profilePostRepository;
    }

    public Optional<Profile> fetchUserProfile(String token) {
        return profilePostRepository.getProfileByToken(token);
    }

    public List<ProfilePostWithComments> fetchUserPosts(String token) {
        return profilePostRepository.getUserPostsWithComments(token);
    }
}