package com.example.flux.application.port.in;

import com.example.flux.domain.model.Sentence;

import java.util.List;

public interface SentenceService {

    List<Sentence> getAllSentences(Long bookId);
    Sentence getSentenceByWordId(Long wordId);
}
