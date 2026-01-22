package com.example.flux.application.service;

import com.example.flux.application.port.in.BookTextImportUseCase;
import com.example.flux.application.port.in.TextParsingService;
import com.example.flux.application.port.out.persistence.BookRepositoryPort;
import com.example.flux.application.port.out.storage.FileStorage;
import com.example.flux.domain.model.Book;
import com.example.flux.domain.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookTextImportService implements BookTextImportUseCase {

    private final BookRepositoryPort bookRepository;
    private final FileStorage fileStorage;
    private final TextParsingService textParsingService;

    @Value("${minio.bucket.name.file}")
    private String bucketNameFile;

    @Transactional
    @Override
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
