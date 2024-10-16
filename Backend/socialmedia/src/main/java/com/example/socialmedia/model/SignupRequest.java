package com.example.socialmedia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record SignupRequest(
    @Id String email,  
    String name,
    String password,
    String mobileNo
) {}
