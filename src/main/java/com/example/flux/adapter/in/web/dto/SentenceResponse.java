package com.example.flux.adapter.in.web.dto;



import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SentenceResponse {
    private Long id;
    private String text;
    private List<WordResponse> words;
}
