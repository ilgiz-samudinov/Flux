package com.example.flux.application.service;

import com.example.flux.application.dto.File;
import com.example.flux.application.port.in.BookService;
import com.example.flux.application.port.in.FileService;
import com.example.flux.application.port.out.persistence.BookRepositoryPort;
import com.example.flux.domain.exception.NotFoundException;
import com.example.flux.domain.model.Book;
import com.example.flux.domain.model.FileObject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepositoryPort bookRepository;
    private final FileService fileService;

    @Value("${minio.bucket.name.cover}")
    private String bucketNameCover;

    @Value("${minio.bucket.name.file}")
    private String bucketNameFile;

    @Transactional
    @Override
    public Book createBook(String title, String author, Integer year,
                           MultipartFile cover, MultipartFile file) {
        FileObject coverObject = fileService.store(cover, bucketNameCover);
        FileObject fileObject = fileService.store(file, bucketNameFile);

        Book book = Book.builder()
                .title(title)
                .author(author)
                .year(year)
                .cover(coverObject)
                .file(fileObject)
                .build();

        return bookRepository.save(book);
    }



    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }



    @Override
    @Transactional(readOnly = true)
    public File getCover(Long id) {
        Book book = getBookById(id);
        return fileService.buildFile(bucketNameCover, book.getCover());
    }


    @Override
    @Transactional(readOnly = true)
    public File getFile(Long id) {
        Book book = getBookById(id);
        return fileService.buildFile(bucketNameFile, book.getFile());
    }


    @Override
    @Transactional(readOnly = true)
    public Book getBookWithDetails(Long id) {
        Book book = bookRepository.findByIdWithSentences(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        book.getSentences()
                .forEach(sentence -> Hibernate.initialize(sentence.getWords()));
        return book;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}
