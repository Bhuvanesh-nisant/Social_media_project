package com.example.socialmedia.service;

import com.example.socialmedia.model.Profile;
import com.example.socialmedia.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> fetchProfile(String token) {
        return profileRepository.getProfileByToken(token);
    }
}
