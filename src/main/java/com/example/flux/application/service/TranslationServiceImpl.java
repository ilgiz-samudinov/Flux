package com.example.flux.application.service;

import com.example.flux.application.port.in.TranslationService;
import com.example.flux.application.port.out.ai.TranslationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationPort translationPort;


    public String translate(String text) {
        try {
            return translationPort.translate(text, "en", "ru");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка перевода: " + e.getMessage(), e);
        }
    }
}
