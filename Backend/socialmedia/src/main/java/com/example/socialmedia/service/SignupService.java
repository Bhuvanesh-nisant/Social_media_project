package com.example.socialmedia.service;

import com.example.socialmedia.model.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    private JdbcClient jdbcClient;

    public boolean checkUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM login WHERE userid = ?";
        Integer count = jdbcClient.sql(sql)
                .param(email)
                .query((rs, rowNum) -> rs.getInt(1))
                .stream()
                .findFirst()
                .orElse(0);  
        return count > 0;
    }

    // Register a new user
    public String registerUser(SignupRequest signupRequest) {
        if (checkUserExists(signupRequest.email())) {
            return "User already exists";
        }

        // Insert into the login table
        String insertLoginSQL = "INSERT INTO login (userid, password) VALUES (?, ?)";
        jdbcClient.sql(insertLoginSQL)
                .param(signupRequest.email())
                .param(signupRequest.password())
                .update();

        // Retrieve the generated token
        String getTokenSQL = "SELECT token FROM login WHERE userid = ?";
        String generatedToken = jdbcClient.sql(getTokenSQL)
                .param(signupRequest.email())
                .query((rs, rowNum) -> rs.getString("token")) // Retrieve the token
                .stream()
                .findFirst()
                .orElse(null); // Handle case where token is not found

        // Insert into the users table with the correct column name
        String insertUserSQL = "INSERT INTO users (email, name, password, mobile_number, token) VALUES (?, ?, ?, ?, ?)";
        jdbcClient.sql(insertUserSQL)
                .param(signupRequest.email())
                .param(signupRequest.name())
                .param(signupRequest.password())
                .param(signupRequest.mobileNo()) // This should correspond to the field in SignupRequest
                .param(generatedToken) // Include the generated token
                .update();

        return "Signup successful";
    }
}
