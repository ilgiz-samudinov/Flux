package com.example.flux.adapter.out.text;

import com.example.flux.adapter.out.text.parser.TextParser;
import com.example.flux.application.port.out.text.TextParserPort;
import com.example.flux.domain.model.Sentence;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@Component
public class TextParserAdapter implements TextParserPort {

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
