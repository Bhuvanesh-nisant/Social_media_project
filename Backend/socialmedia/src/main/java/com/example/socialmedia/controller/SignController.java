package com.example.socialmedia.controller;

import com.example.socialmedia.model.SignupRequest;
import com.example.socialmedia.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")  // Base URL for all requests
@CrossOrigin(origins = "http://localhost:4200")  // Allow frontend cross-origin requests
public class SignController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        String result = signupService.registerUser(signupRequest);
        if ("Signup successful".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
}
