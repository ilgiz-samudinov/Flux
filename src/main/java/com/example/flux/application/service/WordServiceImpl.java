package com.example.flux.application.service;


import com.example.flux.application.port.in.WordService;
import com.example.flux.application.port.out.persistence.WordRepositoryPort;
import com.example.flux.domain.exception.NotFoundException;
import com.example.flux.domain.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepositoryPort wordRepository;

    @Override
    @Transactional(readOnly = true)
    public Word getWordById(Long id) {
        return wordRepository.findById(id).orElseThrow(()-> new NotFoundException("Word not found"));
    }
}
