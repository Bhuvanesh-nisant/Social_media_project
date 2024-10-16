package com.example.socialmedia.repository;

import com.example.socialmedia.model.SignupRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<SignupRequest, String> {
    
}
