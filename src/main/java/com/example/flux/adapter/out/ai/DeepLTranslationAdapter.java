package com.example.flux.adapter.out.ai;

import com.deepl.api.DeepLClient;
import com.deepl.api.TextResult;
import com.example.flux.application.port.out.ai.TranslationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeepLTranslationAdapter implements TranslationPort {

    private final DeepLClient deepLClient;

    @Override
    public String translate(String text, String sourceLang, String targetLang) throws Exception {
        TextResult result = deepLClient.translateText(text, sourceLang, targetLang);
        return result.getText();
    }
}
