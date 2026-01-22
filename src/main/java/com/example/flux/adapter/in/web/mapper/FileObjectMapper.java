package com.example.flux.adapter.in.web.mapper;

import com.example.flux.adapter.in.web.dto.FileObjectResponse;
import com.example.flux.domain.model.FileObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileObjectMapper {
    FileObjectResponse toResponse(FileObject fileObject);
}
