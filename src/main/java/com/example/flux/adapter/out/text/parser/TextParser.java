package com.example.flux.adapter.out.text.parser;

import com.example.flux.domain.model.Sentence;
import com.example.flux.domain.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextParser {

    private final String text;

    public TextParser(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            this.text = lines
                    .filter(l -> !l.isBlank())
                    .collect(Collectors.joining(" "));
        }
    }

    public TextParser(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            this.text = reader.lines()
                    .filter(l -> !l.isBlank())
                    .collect(Collectors.joining(" "));
        }
    }

    private String normalizeText() {
        return text.replaceAll("\\r\\n|\\r|\\n", " ")
                .replaceAll("\\s+", " ");
    }

    public List<Sentence> getSentences() {
        String normalized = normalizeText();

        List<Sentence> sentences = Arrays.stream(normalized.split("(?<=[.!?])\\s+"))
                .map(Sentence::new)
                .collect(Collectors.toList());

        for (Sentence sentence : sentences) {
            List<Word> words = getWords(sentence);
            sentence.setWords(words);
        }

        return sentences;
    }

    public List<Word> getWords(Sentence sentence) {
        List<Word> wordList = new ArrayList<>();
        Pattern pattern = Pattern.compile("[A-Za-z']+");
        Matcher matcher = pattern.matcher(sentence.getText());

        int index = 0;
        while (matcher.find()) {
            String w = matcher.group();
            Word word = new Word(index, w, sentence);
            wordList.add(word);
            index++;
        }

        return wordList;
    }
}
