package com.example.socialmedia.service;

import org.springframework.stereotype.Service;

import com.example.socialmedia.model.PostUpload;
import com.example.socialmedia.repository.PostUploadRepository;

@Service
public class PostUploadService {
    private final PostUploadRepository postUploadRepository;

    public PostUploadService(PostUploadRepository postUploadRepository) {
        this.postUploadRepository = postUploadRepository;
    }

    public boolean uploadPost(PostUpload post) {
        
        return postUploadRepository.savePost(post);
    }
}
