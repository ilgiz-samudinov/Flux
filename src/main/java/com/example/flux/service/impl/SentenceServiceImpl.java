package com.example.flux.service.impl;

import com.example.flux.model.Book;
import com.example.flux.model.Sentence;
import com.example.flux.repository.SentenceRepository;
import com.example.flux.service.BookService;
import com.example.flux.service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SentenceServiceImpl  implements SentenceService {

    private final SentenceRepository sentenceRepository;
    private final BookService bookService;

    @Transactional(readOnly = true)
    @Override
    public List<Sentence> getAllSentences(Long bookId) {
        Book book = bookService.getBookById(bookId);

        return sentenceRepository.findByBookId(bookId);
    }
}
