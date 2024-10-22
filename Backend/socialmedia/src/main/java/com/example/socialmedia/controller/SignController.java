package com.example.socialmedia.controller;

import com.example.socialmedia.model.SignupRequest;
import com.example.socialmedia.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/")  // Base URL for all requests
@CrossOrigin(origins = "http://localhost:4200")  // Allow frontend cross-origin requests
public class SignController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest signupRequest) {
        Map<String, String> response = new HashMap<>();
        String result = signupService.registerUser(signupRequest);

       
        response.put("message", result);

        if ("Signup successful".equals(result)) {
            return ResponseEntity.ok(response);  
        } else {
            return ResponseEntity.status(400).body(response);  
        }
    }
}