package com.example.flux.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class FileObjectResponse {
    private Long id;

    private String objectKey;

    private String contentType;

    private Long size;
}
