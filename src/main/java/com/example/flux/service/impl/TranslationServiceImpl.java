package com.example.flux.service.impl;

import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;
import com.example.flux.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final DeepLClient deepLClient;


    public String translate(String text) {
        try {
            TextResult result = deepLClient.translateText(text, "en" , "ru");
            return result.getText();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка перевода: " + e.getMessage(), e);
        }
    }
}
