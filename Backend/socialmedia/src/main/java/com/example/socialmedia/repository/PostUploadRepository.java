package com.example.socialmedia.repository;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.PostUpload;

@Repository
public class PostUploadRepository {
    private final JdbcClient jdbcClient;

    public PostUploadRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean savePost(PostUpload post) {
        String insertQuery = """
            INSERT INTO posts (username, content, image, likeCount, token) 
            VALUES (?, ?, ?, 0, ?)
        """;

        int rowsInserted = jdbcClient.sql(insertQuery)
            .param(post.username())
            .param(post.content())
            .param(post.image()) // Save Base64 string directly
            .param(post.token())
            .update();

        return rowsInserted > 0; // Return true if the post was successfully inserted
    }
}
