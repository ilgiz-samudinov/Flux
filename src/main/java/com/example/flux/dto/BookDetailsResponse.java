package com.example.flux.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BookDetailsResponse {
    private Long id;
    private String title;
    private Integer year;
    private String author;
    private String  coverUrl;
    private String fileUrl;
    private FileObjectResponse cover;
    private FileObjectResponse file;
    private List<SentenceResponse> sentences;
}
