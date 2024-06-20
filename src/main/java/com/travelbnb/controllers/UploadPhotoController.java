package com.travelbnb.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/photos")
public class UploadPhotoController {
    public String uploadPhoto(){
        return "Photo uploaded";
    }
}
