package com.example.flux.service;

import com.example.flux.model.Book;
import com.example.flux.model.Sentence;
import com.example.flux.port.FileStorage;
import com.example.flux.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookTextImportService {

    private final BookRepository bookRepository;
    private final FileStorage fileStorage;
    private final TextParsingService textParsingService;

    @Value("${minio.bucket.name.file}")
    private String bucketNameFile;

    @Transactional
    public void importFromStorage(Long bookId, String objectKey) throws IOException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        try (InputStream inputStream = fileStorage.getFileAsStream(bucketNameFile, objectKey)) {
            List<Sentence> sentences = textParsingService.parse(inputStream);
            book.addSentences(sentences);
            bookRepository.save(book);
        }
    }
}
