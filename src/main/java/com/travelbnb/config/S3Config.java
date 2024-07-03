package com.travelbnb.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.accessKey}")
    private String awsKeyId;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKeyId, awsSecretKey);
        AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.setRegion(com.amazonaws.regions.RegionUtils.getRegion(awsRegion));
        return amazonS3;
    }

    public AWSCredentials credentials(){
        AWSCredentials credentials  = new BasicAWSCredentials(awsKeyId, awsSecretKey);
        return credentials;
    }


}

