package com.example.flux.controller;


import com.example.flux.dto.TranslationResponse;
import com.example.flux.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TranslationController {

    private final TranslationService translationService;


    @GetMapping("/translate")
    public TranslationResponse translate(@RequestParam String text) {
        String translation = translationService.translate(text);
        return new TranslationResponse(translation);
    }

}
