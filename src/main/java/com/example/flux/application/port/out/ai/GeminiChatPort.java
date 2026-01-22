package com.example.flux.application.port.out.ai;

import dev.langchain4j.model.chat.request.ChatRequest;

public interface GeminiChatPort {
    String chat(ChatRequest request);
}
