package com.example.flux.dto;



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
