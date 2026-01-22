package com.example.flux.application.port.out.ai;

public interface TranslationPort {
    String translate(String text, String sourceLang, String targetLang) throws Exception;
}
