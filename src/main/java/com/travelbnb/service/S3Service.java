package com.travelbnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    //@PostMapping(path = "/upload/file/{bucketName}")
    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file: " + file.getOriginalFilename());
        }

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File name must not be empty");
        }

        try {
            // Create a temporary file in the system's temp directory
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Check if the file was successfully created
            if (!convFile.exists()) {
                throw new IOException("Temporary file not found: " + convFile.getAbsolutePath());
            }

            // Upload the file to S3
            amazonS3.putObject(bucketName, convFile.getName(), convFile);
            return amazonS3.getUrl(bucketName, convFile.getName()).toString();

        } catch (AmazonS3Exception s3Exception) {
            throw new IllegalStateException("Unable to upload file to S3: " + s3Exception.getMessage(), s3Exception);
        } catch (IOException ioException) {
            throw new IllegalStateException("Failed to create or upload file: " + ioException.getMessage(), ioException);
        }
    }
}
