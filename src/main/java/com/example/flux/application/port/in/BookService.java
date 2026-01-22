package com.example.flux.application.port.in;

import com.example.flux.application.dto.File;
import com.example.flux.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Book createBook(String title, String author, Integer year, MultipartFile cover, MultipartFile file);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    File getCover(Long id);
    File getFile(Long id);

    Page<Book> getAllBooks(Pageable pageable);
    Book getBookWithDetails(Long id);

}
