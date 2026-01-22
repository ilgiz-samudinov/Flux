package com.example.flux.adapter.out.ai;

import com.example.flux.application.port.out.ai.PerplexityChatPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerplexityChatAdapter implements PerplexityChatPort {

    private final OpenAiChatModel perplexityAiChatModel;

    @Override
    public String call(Prompt prompt) {
        return perplexityAiChatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}
