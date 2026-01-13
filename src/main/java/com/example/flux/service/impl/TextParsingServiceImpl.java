package com.example.flux.service.impl;

import com.example.flux.model.Sentence;
import com.example.flux.parser.TextParser;
import com.example.flux.service.TextParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TextParsingServiceImpl implements TextParsingService {

    @Override
    public List<Sentence> parse(Path filePath) throws IOException {
        TextParser parser = new TextParser(filePath);
        return parser.getSentences();
    }

    @Override
    public List<Sentence> parse(InputStream inputStream) throws IOException {
        TextParser parser = new TextParser(inputStream);
        return parser.getSentences();
    }
}
