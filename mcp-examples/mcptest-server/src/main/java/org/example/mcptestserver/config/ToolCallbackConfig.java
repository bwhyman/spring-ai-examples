package org.example.mcptestserver.config;

import lombok.AllArgsConstructor;
import org.example.mcptestserver.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ToolCallbackConfig {

    // 注册服务及回调
    @Bean
    public ToolCallbackProvider getToolCallbackProvider(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }
}
