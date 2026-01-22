package com.example.flux.application.service;

import com.example.flux.application.port.in.BookService;
import com.example.flux.application.port.in.SentenceService;
import com.example.flux.application.port.in.WordService;
import com.example.flux.application.port.out.persistence.SentenceRepositoryPort;
import com.example.flux.domain.model.Book;
import com.example.flux.domain.model.Sentence;
import com.example.flux.domain.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SentenceServiceImpl  implements SentenceService {

    private final SentenceRepositoryPort sentenceRepository;
    private final BookService bookService;
    private final WordService wordService;

    @Transactional(readOnly = true)
    @Override
    public List<Sentence> getAllSentences(Long bookId) {
        Book book = bookService.getBookById(bookId);

        return sentenceRepository.findByBookId(bookId);
    }


    @Transactional(readOnly = true)
    @Override
    public Sentence getSentenceByWordId(Long wordId) {
        Word word = wordService.getWordById(wordId);
        return word.getSentence();
    }
}
