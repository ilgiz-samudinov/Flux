package com.example.flux.controller;

import com.example.flux.dto.BookDetailsResponse;
import com.example.flux.dto.BookResponse;
import com.example.flux.dto.File;
import com.example.flux.mapper.BookMapper;
import com.example.flux.model.Book;
import com.example.flux.service.BookService;
import com.example.flux.service.BookTextImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookTextImportService bookTextImportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponse> createBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer year,
            @RequestPart MultipartFile cover,
            @RequestPart MultipartFile file
    ) {
        Book book = bookService.createBook(title, author, year, cover, file);

        String objectKey = book.getFile().getObjectKey();
        try {
            bookTextImportService.importFromStorage(book.getId(), objectKey);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing book text", e);
        }

        return ResponseEntity.ok(bookMapper.toResponse(book));
    }



    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsResponse> getBookDetail(@PathVariable Long id) {
        Book book = bookService.getBookWithDetails(id);
        return ResponseEntity.ok(bookMapper.toDetailsResponse(book));
    }



    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooksSimple() {
        return ResponseEntity.ok(bookService.getAllBooks().stream().map(bookMapper::toResponse).toList());
    }


    @GetMapping("/{id}/cover")
    public ResponseEntity<StreamingResponseBody> downloadCover(@PathVariable Long id) {
        return stream(bookService.getCover(id), "inline");
    }


    @GetMapping("/{id}/file")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable Long id) {
        return stream(bookService.getFile(id), "attachment");
    }


    private ResponseEntity<StreamingResponseBody> stream(File f, String dispositionType) {
        StreamingResponseBody body = out -> {
            try (InputStream in = f.getInputStream()) {
                in.transferTo(out);
            }
        };

        ContentDisposition cd = ContentDisposition.builder(dispositionType)
                .filename(f.getUrl())
                .build();

        return ResponseEntity.ok()
                .contentType(f.getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION, cd.toString())
                .body(body);
    }
}
