package com.example.flux.service.impl;

import com.example.flux.dto.AiTranslateResponseDto;
import com.example.flux.model.Sentence;
import com.example.flux.model.Word;
import com.example.flux.service.AiTranslateService;
import com.example.flux.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerplexityAiTranslateService implements AiTranslateService {

    private final OpenAiChatModel perplexityAiChatModel;
    private final WordService wordService;


    private static final String SYSTEM_PROMPT = """
            Ты — лингвистический помощник для изучения английского языка.
            Твоя задача — анализировать значение английского слова строго в рамках предложенного предложения.

            Входные данные:
            - WORD — английское слово (в точной форме, как в тексте).
            - SENTENCE — английское предложение, содержащее WORD.

            Требования к ответу:
            Верни СТРОГО один валидный JSON-объект и НИЧЕГО кроме JSON:
            - без Markdown,
            - без комментариев,
            - без пояснений вне JSON,
            - только двойные кавычки,
            - только поля, указанные в схеме.

            JSON-структура (с РОВНО тремя переводами в translations):

            {
              "word": {
                "original": "<WORD>",
                "translations": ["<вариант 1>", "<вариант 2>", "<вариант 3>"]
              },
              "contextMeaning": {
                "explanation": "<краткое объяснение значения WORD в данном предложении>",
                "bestFit": "<один вариант из поля translations>"
              },
              "sentence": {
                "original": "<SENTENCE>",
                "translation": "<естественный русский перевод предложения, где bestFit явно выделен **или** [ ]>"
              }
            }

            Строгие правила:
            1. "word.original" = WORD без изменений.
            2. "word.translations":
               - РОВНО 3 варианта.
               - Только современный русский язык.
               - Без архаизмов, устаревших слов, книжных форм и стилистических редкостей.
                 Примеры запрещённых вариантов: "сие", "таковые", "оне", "сию", "данные" (если это не корректный перевод слова).
               - Переводы должны относиться к той же части речи и грамматической форме, что и WORD.
               - Никаких тематических или логически связанных, но НЕ языковых значений.
               - Только реальные словарные переводы.

            3. "contextMeaning.explanation":
               - Кратко, строго по предложению.
               - Не добавлять внешние факты, знания о сюжете, персонажах, вселенной или событиях.
               - Только лингвистическое объяснение функции слова в предложении.

            4. "contextMeaning.bestFit":
               - ДОЛЖЕН быть одним из значений в "word.translations" (строгое совпадение строки).

            5. "sentence.translation":
               - Естественный и корректный русский перевод.
               - Должен включать WORD в контексте.
               - Выделяй bestFit только **жирным** или [квадратными скобками].

            6. Никаких новых полей и расширений схемы.

            Если контекста недостаточно:
            - "contextMeaning.explanation": "Недостаточно контекста".
            - "contextMeaning.bestFit": самый нейтральный вариант из translations.
            - "sentence.translation": максимально буквальный перевод предложения.

            Выведи только JSON.
            """;


    @Override
    public AiTranslateResponseDto getAiTranslate(Long id) {
        Word translateWord = wordService.getWordById(id);
        Sentence sentence = translateWord.getSentence();

        String originalWord = translateWord.getWord();
        String originalSentence = sentence.getText();

        String userContent = """
                WORD: %s
                SENTENCE: %s
                """.formatted(originalWord, originalSentence);


        BeanOutputConverter<AiTranslateResponseDto> outputConverter =
                new BeanOutputConverter<>(AiTranslateResponseDto.class);

        Prompt prompt = new Prompt(
                List.of(
                        new SystemMessage(SYSTEM_PROMPT),
                        new UserMessage(userContent + "\n\n" + outputConverter.getFormat())
                ),
                OpenAiChatOptions.builder()
                        .model("sonar-pro")
                        .temperature(0.0)
                        .maxTokens(800)
                        .responseFormat(new ResponseFormat(
                                ResponseFormat.Type.JSON_SCHEMA,
                                outputConverter.getJsonSchema()
                        ))
                        .build()
        );

        try {
            String raw = perplexityAiChatModel.call(prompt)
                    .getResult()
                    .getOutput()
                    .getText();

            if (raw == null || raw.isBlank()) {
                throw new IllegalStateException("AI returned empty response");
            }

            return outputConverter.convert(raw);

        } catch (Exception e) {

            return new AiTranslateResponseDto(
                    new AiTranslateResponseDto.WordDto(originalWord, List.of("—")),
                    new AiTranslateResponseDto.ContextMeaningDto(
                            "Ошибка генерации. Повтори запрос.",
                            "—"
                    ),
                    new AiTranslateResponseDto.SentenceDto(
                            originalSentence,
                            originalSentence
                    )
            );
        }
    }

}
