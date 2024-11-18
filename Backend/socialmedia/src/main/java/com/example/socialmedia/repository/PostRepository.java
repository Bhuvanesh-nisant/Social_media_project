package com.example.socialmedia.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.socialmedia.model.Post;

@Repository
public class PostRepository {

    @Autowired
    private JdbcClient jdbcClient;

    // Method to insert a post into the database
    public int insertPost(Post post) {
        String sql = """
                INSERT INTO posts (username, postedAgo, content, image, likeCount)
                VALUES (?, ?, ?, ?, ?)
            """;
        return jdbcClient.sql(sql)
                .param("username", post.username())
                .param("postedAgo", post.postedAgo())
                .param("content", post.content())
                .param("image", post.image())
                .param("likeCount", post.likeCount())
                .update();
    }

    // Method to get all posts from the database
    public List<Post> getAllPosts() {
        String sql = "SELECT * FROM posts";

        RowMapper<Post> rowMapper = (rs, rowNum) -> new Post(
                rs.getString("username"),
                rs.getString("postedAgo"),
                rs.getString("content"),
                rs.getBytes("image"),
                rs.getInt("likeCount")
        );

        // Execute the query and return the results as a List<Post>
        return jdbcClient.sql(sql)
                .query(rowMapper) // MappedQuerySpec<Post> is returned
                .stream()  // Convert the result to a stream
                .toList();  // Collect the results into a List<Post>
    }
}
