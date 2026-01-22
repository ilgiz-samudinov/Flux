package com.example.flux.application.port.in;

import com.example.flux.application.dto.AiTranslateResponseDto;

public interface AiTranslateService {
    AiTranslateResponseDto getAiTranslate(Long id);
}
