package com.example.flux.config;

import com.deepl.api.DeepLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepLConfig {

    @Value("${deepl.api.key}")
    private String apiKey;

    @Bean
    public DeepLClient deepLClient() {
        return new DeepLClient(apiKey);
    }
}

