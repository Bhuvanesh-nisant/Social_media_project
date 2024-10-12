package com.example.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories // Ensure this annotation is present
public class SocialmediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialmediaApplication.class, args);
    }
}
