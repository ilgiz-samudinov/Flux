package com.example.flux.application.port.out.persistence;

import com.example.flux.domain.model.Sentence;

import java.util.List;

public interface SentenceRepositoryPort {
    List<Sentence> findByBookId(Long bookId);
}
