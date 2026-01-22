package com.example.flux.adapter.out.persistence.repository;

import com.example.flux.application.port.out.persistence.WordRepositoryPort;
import com.example.flux.domain.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>, WordRepositoryPort {

}
