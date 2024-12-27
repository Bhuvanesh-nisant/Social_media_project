package com.example.socialmedia.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpdateImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int checkTokenExistence(String token) {
        // Query to check if the token already exists in the profile table
        String sql = "SELECT COUNT(*) FROM profile WHERE token = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, token);
    }

    public void updateProfilePhoto(String token, String profilePhotoBase64) {
        // Update profile photo with the provided base64 string
        String sql = "UPDATE profile SET profile_photo = ? WHERE token = ?";
        jdbcTemplate.update(sql, profilePhotoBase64, token);
    }

    public void updateCoverPhoto(String token, String coverPhotoBase64) {
        // Update cover photo with the provided base64 string
        String sql = "UPDATE profile SET cover_photo = ? WHERE token = ?";
        jdbcTemplate.update(sql, coverPhotoBase64, token);
    }

    public void insertProfile(String token, String profilePhotoBase64, String coverPhotoBase64) {
        // Insert new profile with the provided data
        String sql = "INSERT INTO profile (token, profile_photo, cover_photo) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, token, profilePhotoBase64, coverPhotoBase64);
    }
}
