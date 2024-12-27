package com.example.socialmedia.service;

import org.springframework.stereotype.Service;

import com.example.socialmedia.model.UpdateImage;
import com.example.socialmedia.repository.UpdateImageRepository;

@Service
public class UpdateImageService {

    private final UpdateImageRepository updateImageRepository;

    public UpdateImageService(UpdateImageRepository updateImageRepository) {
        this.updateImageRepository = updateImageRepository;
    }

    public void saveOrUpdateProfile(UpdateImage updateImage) {
        String token = updateImage.token();
        String profilePhotoBase64 = updateImage.profilePhoto();
        String coverPhotoBase64 = updateImage.coverPhoto();

        // Check if the token exists in the profile table
        int tokenCount = updateImageRepository.checkTokenExistence(token);

        // If token exists, update the profile or cover photo
        if (tokenCount > 0) {
            if (profilePhotoBase64 != null) {
                updateImageRepository.updateProfilePhoto(token, profilePhotoBase64); // Update profile photo
            }
            if (coverPhotoBase64 != null) {
                updateImageRepository.updateCoverPhoto(token, coverPhotoBase64); // Update cover photo
            }
        } else {
            // Token does not exist, insert a new profile
            updateImageRepository.insertProfile(token, profilePhotoBase64, coverPhotoBase64);
        }
    }
}
