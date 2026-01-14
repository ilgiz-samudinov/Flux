package com.example.flux.service.impl;


import com.example.flux.exception.NotFoundException;
import com.example.flux.model.Word;
import com.example.flux.repository.WordRepository;
import com.example.flux.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Override
    @Transactional(readOnly = true)
    public Word getWordById(Long id) {
        return wordRepository.findById(id).orElseThrow(()-> new NotFoundException("Word not found"));
    }
}
