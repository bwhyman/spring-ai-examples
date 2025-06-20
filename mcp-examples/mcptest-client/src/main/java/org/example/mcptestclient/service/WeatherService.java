package org.example.mcptestclient.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {
    private final ChatClient client;

    public Flux<String> getWeather(String message) {
        return client.prompt()
                .user(message)
                .stream()
                .content();
    }

}
