package com.example.socialmedia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.Profile;
import com.example.socialmedia.model.ProfilePostWithComments;

@Repository
public class ProfilePostRepository {
    private final JdbcClient jdbcClient;

    public ProfilePostRepository(JdbcClient jdbcClient) {
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

    public List<ProfilePostWithComments> getUserPostsWithComments(String token) {
        String fetchPostsQuery = """
            SELECT id, username, postedAgo, content, image, likeCount
            FROM posts
            WHERE token = ?
        """;

        String fetchCommentsQuery = """
            SELECT id, content, timestamp
            FROM comments
            WHERE post_id = ?
        """;

        return jdbcClient.sql(fetchPostsQuery).param(token).query((postRs, postRowNum) -> {
            int postId = postRs.getInt("id");

            List<Comment> comments = jdbcClient.sql(fetchCommentsQuery)
                .param(postId)
                .query((commentRs, commentRowNum) -> new Comment(
                    commentRs.getInt("id"),
                    commentRs.getString("content"),
                    commentRs.getString("timestamp")
                ))
                .list();

            String image = postRs.getString("image");

            return new ProfilePostWithComments(
                postId,
                postRs.getString("username"),
                postRs.getString("postedAgo"),
                postRs.getString("content"),
                image,
                postRs.getInt("likeCount"),
                comments
            );
        }).list();
    }
}
