package org.example.mcptestclient.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mcptestclient.service.WeatherService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/")
public class WeatherController {
    private final WeatherService weatherService;

    // mcp-client对外暴露的接口
    @PostMapping("weathermessage")
    public Flux<String> getCity(@RequestBody Map<String, String> message) {
        return weatherService.getWeather(message.get("message"));
    }

}
