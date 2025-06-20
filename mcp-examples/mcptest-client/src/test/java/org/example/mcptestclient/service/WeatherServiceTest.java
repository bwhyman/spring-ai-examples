package org.example.mcptestclient.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeather() {
        String x = weatherService.getWeather("上海今天的天气怎么样？").blockFirst();
        log.debug(x);
    }
    @Test
    void getWeather2() {
        String x = weatherService.getWeather("今天，北京的天气如何？").blockFirst();
        log.debug(x);
    }
}