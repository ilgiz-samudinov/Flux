package com.example.flux.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer year;
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_file_id")
    private FileObject cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_file_id")
    private FileObject file;

    @Builder.Default
    @OneToMany(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Sentence> sentences = new ArrayList<>();

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
        sentence.setBook(this);
    }

    public void removeSentence(Sentence sentence) {
        sentences.remove(sentence);
        sentence.setBook(null);
    }

    public void addSentences(List<Sentence> sentenceList) {
        for (Sentence sentence : sentenceList) {
            addSentence(sentence);
        }
    }
}
