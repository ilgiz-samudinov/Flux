package com.example.flux.application.port.out.persistence;

import com.example.flux.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    Optional<Book> findById(Long id);
    Optional<Book> findByIdWithSentences(Long id);
    List<Book> findAll();
    Page<Book> findAll(Pageable pageable);
    Book save(Book book);
}
