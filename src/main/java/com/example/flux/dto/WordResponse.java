package com.example.flux.dto;



import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordResponse {
    private Long id;
    private Integer index;
    private String word;
}
