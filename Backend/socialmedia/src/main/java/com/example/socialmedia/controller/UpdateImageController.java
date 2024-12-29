package com.example.socialmedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.model.UpdateImage;
import com.example.socialmedia.service.UpdateImageService;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:4200") 
public class UpdateImageController {

    private final UpdateImageService updateImageService;

    public UpdateImageController(UpdateImageService updateImageService) {
        this.updateImageService = updateImageService;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void uploadProfileOrCoverPhoto(@RequestBody UpdateImage updateImage) {
        updateImageService.saveOrUpdateProfile(updateImage);  // Handle the save or update logic
    }
}
