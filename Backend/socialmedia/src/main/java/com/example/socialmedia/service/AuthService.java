package com.example.socialmedia.service;

import com.example.socialmedia.model.SocialMediaUser;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    public String login(String userid, String password) {
        logger.info("Attempting login for userid: " + userid);
        
        Optional<SocialMediaUser> user = userRepository.findByUseridAndPassword(userid, password);
        
        if (user.isPresent()) {
            logger.info("Login successful for userid: " + userid);
            return "Login success! Token: " + user.get().token();
        } else {
            logger.info("Invalid login for userid: " + userid);
            return "Invalid userid or password";
        }
    }
}
