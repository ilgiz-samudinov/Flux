package com.example.flux.application.service;

import com.example.flux.application.port.in.TextParsingService;
import com.example.flux.application.port.out.text.TextParserPort;
import com.example.flux.domain.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TextParsingServiceImpl implements TextParsingService {

    private final TextParserPort textParserPort;

    @Override
    public List<Sentence> parse(Path filePath) throws IOException {
        return textParserPort.parse(filePath);
    }

    @Override
    public List<Sentence> parse(InputStream inputStream) throws IOException {
        return textParserPort.parse(inputStream);
    }
}
