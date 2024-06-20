package com.travelbnb.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photos")
public class UploadPhotoController {
    @PostMapping("/addPhoto")
    public String uploadPhoto(){
        return "Photo uploaded";
    }
}
