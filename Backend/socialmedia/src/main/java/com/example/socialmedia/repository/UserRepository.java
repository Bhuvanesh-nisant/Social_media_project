package com.example.socialmedia.repository;

import com.example.socialmedia.model.SocialMediaUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<SocialMediaUser, Integer> {
    Optional<SocialMediaUser> findByUseridAndPassword(String userid, String password);
    Optional<SocialMediaUser> findByUserid(String userid);
}
