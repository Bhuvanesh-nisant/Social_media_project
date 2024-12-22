package com.example.socialmedia.repository;

import com.example.socialmedia.model.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

@Repository
public class ProfileRepository {
    private final JdbcClient jdbcClient;

    public ProfileRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Profile> getProfileByToken(String token) {
        // Check if the token exists in the 'users' table
        String userQuery = "SELECT name FROM users WHERE token = ?";
        String name = jdbcClient.sql(userQuery)
                .param(token)
                .query((rs, rowNum) -> rs.getString("name"))
                .stream()
                .findFirst()
                .orElse(null); // If token is not found in users, return null

        if (name == null) {
            return Optional.empty(); // Token not found in users table
        }

        // If token exists in users table, check if there's a corresponding profile
        String profileQuery = """
            SELECT 
                p.bio, 
                p.profile_photo, 
                p.cover_photo 
            FROM 
                profile p 
            WHERE 
                p.token = ?;
        """;

        // Check if profile exists
        Optional<Profile> profile = jdbcClient.sql(profileQuery)
                .param(token)
                .query((rs, rowNum) -> new Profile(
                        rs.getString("bio"),
                        rs.getString("profile_photo"),
                        rs.getString("cover_photo"),
                        name // Use the name from the users table
                ))
                .stream()
                .findFirst();

        // If profile doesn't exist, return a Profile with only the name
        return profile.isPresent() ? profile : Optional.of(new Profile(name)); // Return profile with null fields for missing data
    }
}
