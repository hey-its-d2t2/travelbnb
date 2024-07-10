package com.travelbnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    @PostMapping(path = "/upload/file/{bucketName}, Cons")
    public String uploadFile(MultipartFile file, String bucketName){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file: " + file.getOriginalFilename());
        }

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File name must not be empty");
        }

        try {
            File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                amazonS3.putObject(bucketName,convFile.getName(),convFile);
                return amazonS3.getUrl(bucketName,file.getOriginalFilename()).toString();

            }catch (AmazonS3Exception s3Exception){
                return "Unable to upload file."+s3Exception.getMessage();
            }

        }catch (Exception e){
            throw  new IllegalStateException("Failed to upload file.");
        }
    }

    /*public void uploadFile(MultipartFile file, String bucketNAme) throws IllegalArgumentException, IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file: " + file.getOriginalFilename());
        }

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File name must not be empty");
        }

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(awsS3Bucket, file.getOriginalFilename(), inputStream, metadata);
        } catch (IOException e) {
            throw new IOException("Failed to upload file to S3", e);
        }


    }*/
}