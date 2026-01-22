package com.example.flux.application.port.out.persistence;

import com.example.flux.domain.model.FileObject;

public interface FileObjectRepositoryPort {
    FileObject save(FileObject fileObject);
}
