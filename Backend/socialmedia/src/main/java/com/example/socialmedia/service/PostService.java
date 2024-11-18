package com.example.socialmedia.service;

import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.Comment;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Method to create a post
    public void createPost(Post post) {
        postRepository.insertPost(post);
    }

    // Method to get all posts
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    // Method to get comments for a specific post
    public List<Comment> getCommentsForPost(int postId) {
        return commentRepository.getCommentsForPost(postId);
    }

    // Method to create a comment for a specific post
    public void createComment(int postId, Comment comment) {
        commentRepository.insertComment(new Comment(postId, comment.content(), comment.timestamp()));
    }
}
