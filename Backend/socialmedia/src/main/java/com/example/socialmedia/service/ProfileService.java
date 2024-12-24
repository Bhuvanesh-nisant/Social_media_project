package com.example.socialmedia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.socialmedia.model.PostWithComments;
import com.example.socialmedia.model.Profile;
import com.example.socialmedia.repository.ProfilePostRepository;
import com.example.socialmedia.repository.ProfileRepository;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfilePostRepository profilePostRepository;

    public ProfileService(ProfileRepository profileRepository, ProfilePostRepository profilePostRepository) {
        this.profileRepository = profileRepository;
        this.profilePostRepository = profilePostRepository;
    }

    public Optional<Profile> fetchProfile(String token) {
        return profileRepository.getProfileByToken(token);
    }

    public List<PostWithComments> fetchUserPosts(String token) {
        return profilePostRepository.getUserPostsWithComments(token);
    }
}
