package com.example.flux.service.impl;

import com.example.flux.dto.File;
import com.example.flux.model.FileObject;
import com.example.flux.port.FileStorage;
import com.example.flux.repository.FileObjectRepository;
import com.example.flux.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorage fileStorage;
    private final FileObjectRepository fileObjectRepository;

    @Override
    public FileObject store(MultipartFile file, String bucketName) {
        String objectKey = fileStorage.upload(file, bucketName);

        FileObject fileObject = FileObject.builder()
                .objectKey(objectKey)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

        return fileObjectRepository.save(fileObject);
    }

    @Override
    public File buildFile(String bucketName, FileObject obj) {
        String url = obj.getObjectKey();
        InputStream inputStream = fileStorage.download(bucketName, url);
        String contentType = fileStorage.getContentType(bucketName, url);
        MediaType mediaType = MediaType.parseMediaType(
                contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE
        );

        return File.builder()
                .inputStream(inputStream)
                .url(url)
                .contentType(contentType)
                .mediaType(mediaType)
                .build();
    }
}
