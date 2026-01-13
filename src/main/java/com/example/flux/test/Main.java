package com.example.flux.test;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

public class Main {

    public static void main(String[] args) {

        Client client = new Client();

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .systemInstruction(
                                Content.fromParts(Part.fromText("Ты учитель английского языка. " +
                                        "Твой задача переводить слово контекстом предложение и полностью переводить предожение на русский")))
                        .build();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.5-flash", "слово  'guillotine' предложение: I hardly need to remind the reader about the epic battle between the century's greatest detective, L, and that grotesque murderer, Kira. The instrument of death was a little bit more fantastic than a guillotine (for example), but all Kira accomplished was another reign of terror and a pathetically infantile way of thinking.", config);

        System.out.println(response.text());
    }
}
