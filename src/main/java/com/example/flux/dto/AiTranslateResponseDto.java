package com.example.flux.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiTranslateResponseDto {
    private WordDto word;
    private ContextMeaningDto contextMeaning;
    private SentenceDto sentence;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordDto {
        private String original;
        private List<String> translations;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContextMeaningDto {
        private String explanation;
        private String bestFit;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SentenceDto {
        private String original;
        private String translation;
    }
}
