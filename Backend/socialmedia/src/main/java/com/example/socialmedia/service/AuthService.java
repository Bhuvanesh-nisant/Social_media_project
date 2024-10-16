package com.example.socialmedia.service;

import com.example.socialmedia.model.SocialMediaUser;
import com.example.socialmedia.model.SignupRequest;
import com.example.socialmedia.repository.UserRepository;
import com.example.socialmedia.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;  // For the login table
    @Autowired
    private UserDetailsRepository userDetailsRepository; // For the users table

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    // Login method
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

    // Signup method
    public String signup(SignupRequest signupRequest) {
        // Check if the user already exists in the 'login' table
        Optional<SocialMediaUser> existingUser = userRepository.findByUserid(signupRequest.email());

        if (existingUser.isPresent()) {
            logger.info("User already exists: " + signupRequest.email());
            return "User already exists";
        }

        // Save the user in the 'users' table
        userDetailsRepository.save(signupRequest); // Directly save the SignupRequest

        // Save the user in the 'login' table
        userRepository.save(new SocialMediaUser(
            0, // Assuming auto-generated ID
            signupRequest.email(),
            signupRequest.password(),
            null // No token generation for signup
        ));

        logger.info("Signup successful for user: " + signupRequest.email());
        return "Signup successful";
    }
}
