package com.example.flux.mapper;

import com.example.flux.dto.FileObjectResponse;
import com.example.flux.model.FileObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileObjectMapper {
    FileObjectResponse toResponse(FileObject fileObject);
}
