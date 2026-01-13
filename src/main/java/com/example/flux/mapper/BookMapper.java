package com.example.flux.mapper;

import com.example.flux.dto.BookDetailsResponse;
import com.example.flux.dto.BookResponse;
import com.example.flux.dto.SentenceResponse;
import com.example.flux.dto.WordResponse;
import com.example.flux.model.Book;
import com.example.flux.model.Sentence;
import com.example.flux.model.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FileObjectMapper.class})
public interface BookMapper {

    String MINIO_BASE_URL = "http://localhost:9000/book-covers/";

    @Mapping(target = "coverUrl", expression = "java(book.getCover() != null ? MINIO_BASE_URL + book.getCover().getObjectKey() : null)")
    BookResponse toResponse(Book book);

    @Mapping(target = "coverUrl", expression = "java(book.getCover() != null ? MINIO_BASE_URL + book.getCover().getObjectKey() : null)")
    @Mapping(target = "fileUrl", expression = "java(book.getFile() != null ? MINIO_BASE_URL + book.getFile().getObjectKey() : null)")
    BookDetailsResponse toDetailsResponse(Book book);


    SentenceResponse toSentenceResponse(Sentence sentence);
    List<SentenceResponse> toSentenceResponses(List<Sentence> sentences);

    WordResponse toWordResponse(Word word);
    List<WordResponse> toWordResponses(List<Word> words);


}

