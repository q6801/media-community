//package com.example.mediacommunity.community.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import static org.mockito.BDDMockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AmazonS3ServiceTest {
//    @Mock
//    AmazonS3 amazonS3;
//
//    @InjectMocks
//    AmazonS3Service amazonS3Service;
//
//    @Test
//    public String successToSearchDefaultProfile() {
//        String bucket = "/static";
//        String filename = "/img";
//        ReflectionTestUtils.setField(amazonS3Service, "bucket", bucket);
//        given(amazonS3.getUrl(bucket, filename))
//                .willReturn();
//        String fileName = "static/img/default-profile.png";
//        return amazonS3.getUrl(bucket, fileName).toString();
//    }
//}
