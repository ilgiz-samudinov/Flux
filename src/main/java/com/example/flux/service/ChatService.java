package com.example.flux.service;


import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String SYSTEM_PROMPT =
            "You are a helpful assistant. Answer clearly and concisely. " +
            "If the question is ambiguous, ask a brief clarifying question.";

    private final ChatModel chatModel;


    public String simpleChat(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message must not be blank");
        }

        ChatResponse chatResponse = chatModel.chat(ChatRequest.builder()
                .messages(
                        SystemMessage.from(SYSTEM_PROMPT),
                        UserMessage.from(message)
                )
                .build());

        return  chatResponse.aiMessage().text();
    }

}

