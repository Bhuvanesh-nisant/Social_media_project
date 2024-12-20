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
        String query = """
            SELECT 
                p.bio, 
                p.profile_photo, 
                p.cover_photo, 
                u.name 
            FROM 
                profile p 
            JOIN 
                users u 
            ON 
                p.token = u.token 
            WHERE 
                p.token = ?;
        """;

        RowMapper<Profile> rowMapper = (rs, rowNum) -> new Profile(
            rs.getString("bio"),
            rs.getString("profile_photo"),
            rs.getString("cover_photo"),
            rs.getString("name")
        );

        return jdbcClient.sql(query)
                .param(token)
                .query(rowMapper)
                .optional(); // Use `optional()` to handle null gracefully
    }
}
