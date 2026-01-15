package com.example.flux.controller;

import com.example.flux.dto.AiTranslateResponseDto;
import com.example.flux.service.AiTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/translate")
public class AiTranslationController {

    private final AiTranslateService aiTranslateService;

    @GetMapping("/{id}")
    public AiTranslateResponseDto translate(@PathVariable Long id) {
        return aiTranslateService.getAiTranslate(id);
    }

}
