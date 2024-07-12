package com.travelbnb.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class S3Config {

    /*@Value("${aws.accessKey}")
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
*/

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${region}")
    private String region;

    public AWSCredentials credentials(){
        AWSCredentials credentials=new BasicAWSCredentials(accessKey,secretKey);
        return credentials;
    }
    @Bean
    public AmazonS3 amazonS3(){

        AmazonS3 s3client= AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(region)
                .build();
        return s3client;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofGigabytes(5)); // Set the maximum file size to 5GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(5)); // Set the maximum request size to 5GB
        return factory.createMultipartConfig();
    }

}

