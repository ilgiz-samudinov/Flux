package com.example.flux.service;

import com.example.flux.dto.File;
import com.example.flux.model.FileObject;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileObject store(MultipartFile file, String bucketName);
    File buildFile(String bucketName, FileObject obj);
}
