package com.example.flux.service.impl;

import com.example.flux.dto.AiTranslateResponseDto;
import com.example.flux.model.Sentence;
import com.example.flux.model.Word;
import com.example.flux.service.AiTranslateService;
import com.example.flux.service.WordService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiAiTranslateService implements AiTranslateService {

    private final WordService wordService;
    private final ChatModel chatModel;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String SYSTEM_PROMPT = """
            Ты — языковой помощник для изучения английского языка.
            Твоя задача — анализировать английское слово только на основе предложенного предложения и выдавать строго структурированный результат.

            Входные данные:
            - WORD — английское слово в точной форме из оригинального текста.
            - SENTENCE — английское предложение, в котором встречается WORD.

            Требования к ответу:
            Верни СТРОГО один валидный JSON-объект и НИЧЕГО, кроме JSON:
            - без Markdown,
            - без любых пояснений вне JSON,
            - без добавления текста до или после JSON,
            - только двойные кавычки,
            - только поля, указанные в схеме.

            JSON-структура (translations ДОЛЖЕН содержать РОВНО 3 варианта):

            {
              "word": {
                "original": "<WORD>",
                "translations": ["<вариант 1>", "<вариант 2>", "<вариант 3>"]
              },
              "contextMeaning": {
                "explanation": "<кратко объясни значение WORD в данном предложении>",
                "bestFit": "<один из вариантов translations>"
              },
              "sentence": {
                "original": "<SENTENCE>",
                "translation": "<естественный и связный перевод предложения на русский язык, где bestFit ВЫДЕЛЕН с помощью **или** [ ]>"
              }
            }

            Строгие правила:
            1. "word.original" полностью повторяет WORD.
            2. "word.translations":
               - РОВНО три значения.
               - Только корректные словарные переводы WORD без контекста.
               - Только современный русский язык, без архаизмов или устаревших форм.
                 Примеры запрещённых вариантов: "сие", "таковые", "оне", "сию".
               - Никаких тематических или условных смыслов (например, "данные" для слова these).
               - Только та же часть речи и грамматическая форма, что и WORD в предложении.
            3. "contextMeaning.explanation":
               - Краткое, чёткое, только про функцию WORD в данном предложении.
               - Не использовать знания о персонажах, мире или сюжете — только лингвистику.
            4. "contextMeaning.bestFit":
               - ДОЛЖЕН быть ровно одним из значений в "word.translations".
            5. "sentence.translation":
               - Должен быть естественным и грамматически правильным.
               - Перевод words не объясняет, а переводит.
               - bestFit обязан быть явно выделен (**или** [ ]).
            6. Никаких новых ключей, никаких комментариев.

            Если контекста недостаточно:
            - "contextMeaning.explanation": "Недостаточно контекста"
            - "contextMeaning.bestFit": наиболее нейтральный вариант из translations
            - "sentence.translation": буквальный русский перевод предложения

            Выведи только один JSON-объект.
            """;


    @Override
    public AiTranslateResponseDto getAiTranslate(Long id) {
        Word translateWord = wordService.getWordById(id);
        Sentence sentence = translateWord.getSentence();

        String originalWord = translateWord.getWord();
        String originalSentence = sentence.getText();

        ChatRequest request = ChatRequest.builder()
                .messages(List.of(
                        SystemMessage.from(SYSTEM_PROMPT),
                        UserMessage.from("WORD: " + originalWord),
                        UserMessage.from("SENTENCE: " + originalSentence)
                ))
                .build();

        ChatResponse chatResponse = chatModel.chat(request);
        String json = chatResponse.aiMessage().text();

        try {
            return objectMapper.readValue(json, AiTranslateResponseDto.class);
        } catch (Exception e) {
            return new AiTranslateResponseDto(
                    new AiTranslateResponseDto.WordDto(originalWord, List.of()),
                    new AiTranslateResponseDto.ContextMeaningDto(
                            "Модель вернула невалидный JSON. Повтори запрос или усили системный промпт.",
                            ""
                    ),
                    new AiTranslateResponseDto.SentenceDto(originalSentence, "")
            );
        }
    }
}
