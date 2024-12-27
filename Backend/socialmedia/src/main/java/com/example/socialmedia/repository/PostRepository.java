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
        String fetchPostsQuery = """
            SELECT id, username, postedAgo, content, image, likeCount
            FROM posts
             ORDER BY postedAgo DESC
        """;

        String fetchCommentsQuery = """
            SELECT id, content, timestamp
            FROM comments
            WHERE post_id = ?
        """;

        return jdbcClient.sql(fetchPostsQuery).query((postRs, postRowNum) -> {
            int postId = postRs.getInt("id");

            // Fetch comments for each post
            List<Comment> comments = jdbcClient.sql(fetchCommentsQuery)
                .param(postId)
                .query((commentRs, commentRowNum) -> new Comment(
                    commentRs.getInt("id"),
                    commentRs.getString("content"),
                    commentRs.getString("timestamp")
                ))
                .list();

            // Return the 'image' field as it is (Base64 or raw value)
            String image = postRs.getString("image");

            return new PostWithComments(
                postId,
                postRs.getString("username"),
                postRs.getString("postedAgo"),
                postRs.getString("content"),
                image,  // Raw image string from DB
                postRs.getInt("likeCount"),
                comments
            );
        }).list();
    }

    public boolean incrementLikeCount(int postId) {
        String updateQuery = "UPDATE posts SET likeCount = likeCount + 1 WHERE id = ?";
        return jdbcClient.sql(updateQuery).param(postId).update() > 0;
    }

    public void addComment(int postId, Comment comment) {
        String insertQuery = "INSERT INTO comments (post_id, content) VALUES (?, ?)";
        jdbcClient.sql(insertQuery)
            .param(postId)
            .param(comment.content())
            .update();
    }
}
