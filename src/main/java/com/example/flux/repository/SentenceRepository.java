package com.example.flux.repository;

import com.example.flux.model.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long> {
    List<Sentence> findByBookId(Long bookId);

}
