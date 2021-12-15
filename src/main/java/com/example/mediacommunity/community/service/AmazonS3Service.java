package com.example.mediacommunity.community.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String searchIcon() {
        String fileName = "static/img/searchicon.png";
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String searchDefaultProfile() {
        String fileName = "static/img/default-profile.png";
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String searchImage(String path, String fileName) {
        return amazonS3.getUrl(bucket, path + fileName).toString();
    }

    public String uploadImg(String fileName, MultipartFile multipartFile) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        String contentType = multipartFile.getContentType();
        metadata.setContentType(contentType);
        amazonS3.putObject(new PutObjectRequest(bucket, fileName,
                multipartFile.getInputStream(), metadata));
        return "uploadS3 success";
    }

    public void deleteFile(String bucket, String deleteFile) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, deleteFile));
    }
}