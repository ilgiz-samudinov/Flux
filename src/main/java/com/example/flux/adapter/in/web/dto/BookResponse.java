package com.example.flux.adapter.in.web.dto;

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
