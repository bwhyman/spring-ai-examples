package org.example.mcptestserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

    @Tool(description = "查询城市天气")
    public String getWeather(@ToolParam(description = "城市名称") String cityName) {
        log.debug("AiService");
        return map.getOrDefault(cityName, "对不起，未找到城市");
    }

    // 模拟
    private final Map<String, String> map = getMap();
    private Map<String, String> getMap() {
        return Map.of("北京", "雾霾", "上海", "天气好");
    }
}
