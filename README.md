# Flux

Spring Boot service for managing books, sentences, translations, and file storage.

## Stack
- Java 21, Spring Boot 4, Maven
- PostgreSQL, RabbitMQ, MinIO
- DeepL, Perplexity (OpenAI-compatible), Google Gemini

## Getting started
1. Install Java 21.
2. Export required environment variables (see `.env.example`).
3. Start dependencies (PostgreSQL, RabbitMQ, MinIO) using the defaults in `src/main/resources/application.yml`.
4. Run the app:

```bash
./mvnw spring-boot:run
```

## Notes
- HTTP examples live in `src/test/java/com/example/flux/*.http`.
