package com.travelbnb.controllers;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Images;
import com.travelbnb.entity.Property;
import com.travelbnb.repository.ImagesRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;
    private S3Service s3Service;

    public ImageController(PropertyRepository propertyRepository, ImagesRepository imagesRepository, S3Service s3Service) {
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
        this.s3Service = s3Service;
    }


    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Images> uploadPropertyPhotos(
           @RequestParam MultipartFile file,
           @PathVariable String bucketName,
           @PathVariable long propertyId,
           @AuthenticationPrincipal AppUser user
           ){
        Property property = propertyRepository.findById(propertyId).get();
        String imageUrl = s3Service.uploadFile(file, bucketName);
        Images images  = new Images();
        images.setProperty(property);
        images.setImage_url(imageUrl);

        Images savedImageEntity = imagesRepository.save(images);
        return new ResponseEntity<>(savedImageEntity, HttpStatus.CREATED);


    }
}
