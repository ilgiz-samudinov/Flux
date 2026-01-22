package com.example.flux.application.port.in;

import com.example.flux.domain.model.Word;

public interface WordService {
    Word getWordById(Long id);
}
