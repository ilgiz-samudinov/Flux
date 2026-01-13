package com.example.flux.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {
    private Long id;
    private String title;
    private Integer year;
    private String author;
    private String  coverUrl;
}
