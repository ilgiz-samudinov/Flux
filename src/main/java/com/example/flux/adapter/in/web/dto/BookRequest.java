package com.example.flux.adapter.in.web.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BookRequest {
    private String title;
    private Integer year;
    private String author;
    private MultipartFile cover;
    private MultipartFile file;

}