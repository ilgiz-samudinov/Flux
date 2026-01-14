package com.example.flux.controller;


import com.example.flux.dto.SentenceResponse;
import com.example.flux.mapper.SentenceMapper;
import com.example.flux.model.Sentence;
import com.example.flux.service.SentenceService;
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


    @GetMapping ("/{id}")
    public ResponseEntity<List<SentenceResponse>> getAllSentences(@PathVariable Long id) {

        return ResponseEntity.ok(sentenceService.getAllSentences(id).stream().map(sentenceMapper::toResponse).toList());
    }


    @GetMapping("/{wordId}/word")
    public ResponseEntity<SentenceResponse> getSentence(@PathVariable Long wordId) {
        Sentence sentence = sentenceService.getSentenceByWordId(wordId);
        return ResponseEntity.ok(sentenceMapper.toResponse(sentence));
    }


}
