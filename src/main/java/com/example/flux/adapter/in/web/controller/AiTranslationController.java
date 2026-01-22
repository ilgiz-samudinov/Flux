package com.example.flux.adapter.in.web.controller;

import com.example.flux.application.dto.AiTranslateResponseDto;
import com.example.flux.application.port.in.AiTranslateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/translate")
public class AiTranslationController {

    private final AiTranslateService perplexityAiTranslateService;
    private final AiTranslateService geminiAiTranslateService;

    public AiTranslationController(
            @Qualifier("perplexityAiTranslateService") AiTranslateService perplexityAiTranslateService,
            @Qualifier("geminiAiTranslateService") AiTranslateService geminiAiTranslateService
    ) {
        this.perplexityAiTranslateService = perplexityAiTranslateService;
        this.geminiAiTranslateService = geminiAiTranslateService;
    }


    @GetMapping("/perplexity/{id}")
    public AiTranslateResponseDto translatePerplexityAi(@PathVariable Long id) {
        return perplexityAiTranslateService.getAiTranslate(id);
    }

    @GetMapping("/gemini/{id}")
    public AiTranslateResponseDto translateGeminiAi(@PathVariable Long id) {
        return geminiAiTranslateService.getAiTranslate(id);
    }
}
