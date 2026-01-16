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
public class AiTranslateServiceImpl implements AiTranslateService {

    private final WordService wordService;
    private final ChatModel chatModel;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String SYSTEM_PROMPT = """
                     Ты помощник для изучения английского языка.
       
                     Тебе предоставляются:
                     - WORD — английское слово
                     - SENTENCE — английское предложение, содержащее это слово
            
                     Твоя задача — проанализировать WORD и SENTENCE и вернуть ответ строго на основе этих данных.
           
                     Верни ответ СТРОГО в виде валидного JSON (один JSON-объект) и НИЧЕГО кроме JSON:
                     - без Markdown
                     - без пояснений вне JSON
                     - без лишних полей
                     - используй только двойные кавычки
           
                     JSON должен строго соответствовать следующей схеме:
            
                     {
                       "word": {
                         "original": "<WORD>",
                         "translations": ["<вариант 1>", "<вариант 2>", "<вариант 3>"]
                       },
                       "contextMeaning": {
                         "explanation": "<объяснение значения WORD в данном предложении (кратко, по делу)>",
                         "bestFit": "<один наиболее подходящий перевод из списка translations>"
                       },
                       "sentence": {
                         "original": "<SENTENCE>",
                         "translation": "<полный естественный перевод предложения на русский язык, где перевод bestFit ЯВНО выделен, например, с помощью ** или [ ]>"
                       }
                     }
        
                     Обязательные правила:
                     - "word.original" всегда равен WORD (как он дан).
                     - "word.translations" содержит несколько возможных переводов WORD без контекста.
                     - "contextMeaning.bestFit" обязан быть одним из значений из "word.translations".
                     - В "sentence.translation" перевод bestFit должен быть явно визуально выделен.
                     - Не добавляй новых полей.
                     - Не используй внешние знания, не связанные с предложением.
                     - Не придумывай значения, которых нельзя вывести из контекста.

                     Если контекста недостаточно:
                     - всё равно верни JSON по указанной схеме,
                     - в "contextMeaning.explanation" напиши "Недостаточно контекста",
                     - "contextMeaning.bestFit" выбери максимально нейтральный вариант из translations,
                     - "sentence.translation" сделай максимально буквальным.

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
