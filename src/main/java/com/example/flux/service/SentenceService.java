package com.example.flux.service;

import com.example.flux.model.Sentence;

import java.util.List;

public interface SentenceService {

    List<Sentence> getAllSentences(Long bookId);
}
