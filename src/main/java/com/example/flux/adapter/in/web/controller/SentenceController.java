package com.example.flux.adapter.in.web.controller;


import com.example.flux.adapter.in.web.dto.SentenceResponse;
import com.example.flux.adapter.in.web.mapper.SentenceMapper;
import com.example.flux.application.port.in.SentenceService;
import com.example.flux.domain.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sentences")
@RequiredArgsConstructor
public class SentenceController {

    private final SentenceService sentenceService;
    private final SentenceMapper sentenceMapper;


    @GetMapping ("/{bookId}")
    public ResponseEntity<List<SentenceResponse>> getAllSentences(@PathVariable Long bookId) {

        return ResponseEntity.ok(sentenceService.getAllSentences(bookId).stream().map(sentenceMapper::toResponse).toList());
    }


    @GetMapping("/{wordId}/word")
    public ResponseEntity<SentenceResponse> getSentence(@PathVariable Long wordId) {
        Sentence sentence = sentenceService.getSentenceByWordId(wordId);
        return ResponseEntity.ok(sentenceMapper.toResponse(sentence));
    }


}
