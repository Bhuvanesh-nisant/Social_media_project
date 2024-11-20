package com.example.socialmedia.repository;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.PostWithComments;

@Repository
public class PostRepository {
    private final JdbcClient jdbcClient;

    public PostRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<PostWithComments> getAllPostsWithComments() {
        String postQuery = """
            SELECT id, username, postedAgo, content, image, likeCount
            FROM posts
        """;

        String commentQuery = """
            SELECT id, content, timestamp
            FROM comments
            WHERE post_id = ?
        """;

        return jdbcClient.sql(postQuery).query((rs, rowNum) -> {
            int postId = rs.getInt("id");
            List<Comment> comments = jdbcClient.sql(commentQuery)
                .param(postId)
                .query((crs, crowNum) -> new Comment(
                    crs.getInt("id"),
                    crs.getString("content"),
                    crs.getString("timestamp")
                ))
                .list();

            return new PostWithComments(
                postId,
                rs.getString("username"),
                rs.getString("postedAgo"),
                rs.getString("content"),
                rs.getBytes("image"),
                rs.getInt("likeCount"),
                comments
            );
        }).list();
    }
}
