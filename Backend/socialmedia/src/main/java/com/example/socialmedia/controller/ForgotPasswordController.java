package com.example.socialmedia.controller;

import com.example.socialmedia.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class ForgotPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    // Endpoint for user verification
    @PostMapping("/forgot-password/verify")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String userid = request.get("userid");

        logger.info("Received verification request for name: {}, userid: {}", name, userid);
        
        Map<String, String> response = new HashMap<>();
        try {
            if (forgotPasswordService.verifyUser(name, userid)) {
                response.put("message", "User verified");
                logger.info("User verified successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "User ID or Name mismatch.");
                logger.warn("User verification failed: ID or Name mismatch");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            logger.error("Error during verification: ", e);
            response.put("message", "Verification failed due to an internal error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint for password reset
    @PostMapping("/forgot-password/reset")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String userid = request.get("userid");
        String newPassword = request.get("newPassword");

        logger.info("Received password reset request for userid: {}", userid);

        forgotPasswordService.updatePassword(userid, newPassword);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password updated successfully.");
        logger.info("Password updated successfully for userid: {}", userid);
        return ResponseEntity.ok(response);
    }
}
