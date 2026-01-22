package com.example.flux.adapter.out.persistence.repository;

import com.example.flux.application.port.out.persistence.SentenceRepositoryPort;
import com.example.flux.domain.model.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>, SentenceRepositoryPort {
    List<Sentence> findByBookId(Long bookId);

}
