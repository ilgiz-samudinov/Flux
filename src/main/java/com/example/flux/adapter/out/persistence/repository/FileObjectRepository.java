package com.example.flux.adapter.out.persistence.repository;


import com.example.flux.application.port.out.persistence.FileObjectRepositoryPort;
import com.example.flux.domain.model.FileObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileObjectRepository extends JpaRepository<FileObject, Long>, FileObjectRepositoryPort {
}
