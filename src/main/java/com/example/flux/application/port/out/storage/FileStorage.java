package com.example.flux.application.port.out.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorage {
    String upload(MultipartFile file, String bucketName);
    InputStream download(String bucketName, String key);

    String getContentType(String bucketName, String key);
    void delete(String bucketName, String key);
    InputStream getFileAsStream(String bucketName, String objectKey);
}
