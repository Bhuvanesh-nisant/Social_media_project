package com.example.socialmedia.repository;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.Profile;

@Repository
public class ProfileRepository {
    private final JdbcClient jdbcClient;

    public ProfileRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Profile> getProfileByToken(String token) {
        String userQuery = "SELECT name FROM users WHERE token = ?";
        String name = jdbcClient.sql(userQuery)
                .param(token)
                .query((rs, rowNum) -> rs.getString("name"))
                .stream()
                .findFirst()
                .orElse(null);

        if (name == null) {
            return Optional.empty();
        }

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

        Optional<Profile> profile = jdbcClient.sql(profileQuery)
                .param(token)
                .query((rs, rowNum) -> new Profile(
                        rs.getString("bio"),
                        rs.getString("profile_photo"),
                        rs.getString("cover_photo"),
                        name
                ))
                .stream()
                .findFirst();

        return profile.isPresent() ? profile : Optional.of(new Profile(name));
    }
}
