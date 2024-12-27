package com.example.socialmedia.controller;

import com.example.socialmedia.model.UpdateImage;
import com.example.socialmedia.service.UpdateImageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
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
