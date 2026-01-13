package com.example.flux.controller;


import com.example.flux.dto.GenerationResponse;
import com.example.flux.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/ai/simple")
    public GenerationResponse simpleChat(@RequestParam("message") String message) {
        return new GenerationResponse(chatService.simpleChat(message));
    }
}
