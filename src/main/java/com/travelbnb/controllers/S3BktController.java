package com.travelbnb.controllers;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.travelbnb.service.S3Service;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/s3bkt")
public class S3BktController {

    /*@Autowired
    private AmazonS3 amazonS3;*/

   /* @Value("${aws.s3.bucket}")
    private String awsS3Bucket;*/

    @Autowired
    private S3Service service;

    @PostMapping(value = "/upload/file/{bucketName}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable String bucketName
    ) {

        return new ResponseEntity<>(service.uploadFile(file,bucketName),HttpStatus.OK);
    }

   /* @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        S3Object s3Object = amazonS3.getObject(awsS3Bucket, fileName);
        InputStream inputStream = s3Object.getObjectContent();
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(s3Object.getObjectMetadata().getContentLength());
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }*/

}
