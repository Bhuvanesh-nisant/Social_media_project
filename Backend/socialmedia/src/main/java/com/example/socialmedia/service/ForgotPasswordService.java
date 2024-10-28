package com.example.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ForgotPasswordService {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
    private final JdbcClient jdbcClient;

    @Autowired
    public ForgotPasswordService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Verifies if the user exists based on name and userid
    public boolean verifyUser(String name, String userid) {
        logger.info("Verifying user with name: {} and userid: {}", name, userid);
        
        String query = "SELECT COUNT(*) FROM users WHERE name = ? AND email = ?";
        Integer userExists = jdbcClient.sql(query)
                .param(name)
                .param(userid)
                .query(Integer.class)
                .optional()
                .orElse(0);
        
        logger.info("User exists count: {}", userExists);
        return userExists > 0;
    }

    // Updates the password in both login and users tables
    public void updatePassword(String userid, String newPassword) {
        logger.info("Updating password for userid: {}", userid);
        
        // Update in login table
        String updateQueryLogin = "UPDATE login SET password = ? WHERE userid = ?";
        jdbcClient.sql(updateQueryLogin)
                .param(newPassword)
                .param(userid)
                .update();
        
        logger.info("Password updated in login table for userid: {}", userid);

        // Update in users table
        String updateQueryUsers = "UPDATE users SET password = ? WHERE email = ?";
        jdbcClient.sql(updateQueryUsers)
                .param(newPassword)
                .param(userid)  // Ensure this is the correct identifier for users table
                .update();

        logger.info("Password updated in users table for userid: {}", userid);
    }
}
