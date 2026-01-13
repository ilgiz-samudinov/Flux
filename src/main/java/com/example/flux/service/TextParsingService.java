package com.example.flux.service;

import com.example.flux.model.Sentence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface TextParsingService {
    List<Sentence> parse(Path filePath) throws IOException;
    List<Sentence> parse(InputStream inputStream) throws IOException;
}
