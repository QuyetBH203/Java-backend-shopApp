package com.project.shopapp.services;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.shopapp.responses.FileUploadResponse;
import com.project.shopapp.exceptions.FileUploadException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileUploadService implements IFileUploadService {
    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.folder}")
    private String folder;

    private AmazonS3 s3Client;


    //This method is automatically executed after the bean is created.
    //Instead of creating a new S3 client every time a file is uploaded,
    // it is initialized once and reused.
    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
    }

    @Override
    public List<FileUploadResponse> uploadFiles(List<MultipartFile> multipartFiles) {
        List<FileUploadResponse> responses = new ArrayList<>();
        log.info(LocalDate.now().toString());
        log.info(folder);

        for (MultipartFile file : multipartFiles) {
            try {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());
                objectMetadata.setContentLength(file.getSize());
                long key = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                String filePath = folder + "/" + key + "-" + file.getOriginalFilename();
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);
                s3Client.putObject(putObjectRequest);
                String imageUrl = s3Client.getUrl(bucketName, filePath).toString();
                FileUploadResponse response = new FileUploadResponse();
                response.setFilePath(imageUrl);
                response.setDateTime(LocalDateTime.now());
                responses.add(response);
            } catch (IOException e) {
                log.error("Error occurred while uploading file {} ==> {}", file.getOriginalFilename(), e.getMessage());
                throw new FileUploadException("Error occurred in file upload ==> " + e.getMessage());
            }
        }
        return responses;
    }
}
