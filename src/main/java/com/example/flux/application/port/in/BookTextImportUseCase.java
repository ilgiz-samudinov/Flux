package com.example.flux.application.port.in;

import java.io.IOException;

public interface BookTextImportUseCase {
    void importFromStorage(Long bookId, String objectKey) throws IOException;
}
