package com.example.socialmedia.controller;

import com.example.socialmedia.model.SignupRequest;
import com.example.socialmedia.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        String result = authService.signup(signupRequest);
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userid, @RequestParam String password) {
        String result = authService.login(userid, password);
        
        if (result.startsWith("Login success")) {
            String token = result.substring(result.indexOf("Token: ") + 7);
            return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Login failed"));
        }
    }
}
