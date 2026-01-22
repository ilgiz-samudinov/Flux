package com.example.flux.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class YandexTranslateClient {

    private static final String API_URL =
            "https://translate.api.cloud.yandex.net/translate/v2/translate";

    private final HttpClient httpClient;
    private final String apiKey;

    public YandexTranslateClient(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.apiKey = apiKey;
    }

    public String translate(String text, String sourceLang, String targetLang) throws Exception {
        String jsonBody = """
            {
              "sourceLanguageCode": "%s",
              "targetLanguageCode": "%s",
              "texts": ["%s"]
            }
            """.formatted(
                sourceLang,
                targetLang,
                escapeJson(text)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Api-Key " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Translate API error: " + response.statusCode() + " " + response.body()
            );
        }

        return response.body();
    }

    private static String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
