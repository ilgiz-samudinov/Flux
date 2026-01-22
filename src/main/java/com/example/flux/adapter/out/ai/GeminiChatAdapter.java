package com.example.flux.adapter.out.ai;

import com.example.flux.application.port.out.ai.GeminiChatPort;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiChatAdapter implements GeminiChatPort {

    private final ChatModel chatModel;

    @Override
    public String chat(ChatRequest request) {
        return chatModel.chat(request).aiMessage().text();
    }
}
