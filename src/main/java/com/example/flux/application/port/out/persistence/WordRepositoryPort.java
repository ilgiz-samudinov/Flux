package com.example.flux.application.port.out.persistence;

import com.example.flux.domain.model.Word;

import java.util.Optional;

public interface WordRepositoryPort {
    Optional<Word> findById(Long id);
}
