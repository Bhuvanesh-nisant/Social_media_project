package com.example.socialmedia.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.Comment;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcClient jdbcClient;

    // Method to insert a comment into the database
    public int insertComment(Comment comment) {
        String sql = """
                INSERT INTO comments (post_id, content, timestamp)
                VALUES (?, ?, ?)
            """;
        return jdbcClient.sql(sql)
                .param("post_id", comment.postId())
                .param("content", comment.content())
                .param("timestamp", comment.timestamp())
                .update();
    }

    // Method to get comments for a specific post from the database
    public List<Comment> getCommentsForPost(int postId) {
        String sql = "SELECT * FROM comments WHERE post_id = ?";

        RowMapper<Comment> rowMapper = (rs, rowNum) -> new Comment(
                rs.getInt("post_id"),
                rs.getString("content"),
                rs.getString("timestamp")
        );

        // Execute the query and return the results as a List<Comment>
        return jdbcClient.sql(sql)
                .param("post_id", postId)
                .query(rowMapper) // MappedQuerySpec<Comment> is returned
                .stream()  // Convert the result to a stream
                .toList();  // Collect the results into a List<Comment>
    }
}
