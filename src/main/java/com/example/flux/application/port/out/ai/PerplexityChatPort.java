package com.example.flux.application.port.out.ai;

import org.springframework.ai.chat.prompt.Prompt;

public interface PerplexityChatPort {
    String call(Prompt prompt);
}
