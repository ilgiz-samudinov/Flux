package com.example.flux.application.port.in;

import com.example.flux.application.dto.File;
import com.example.flux.domain.model.FileObject;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileObject store(MultipartFile file, String bucketName);
    File buildFile(String bucketName, FileObject obj);
}
