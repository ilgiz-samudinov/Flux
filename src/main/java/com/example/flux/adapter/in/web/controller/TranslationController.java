package com.example.flux.adapter.in.web.controller;


import com.example.flux.adapter.in.web.dto.TranslationResponse;
import com.example.flux.application.port.in.TranslationService;
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
