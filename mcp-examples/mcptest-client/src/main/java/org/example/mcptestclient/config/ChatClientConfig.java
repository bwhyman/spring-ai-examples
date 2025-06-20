package org.example.mcptestclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatClientConfig {

    // 必须注入toolCallbackProvider，否则直接调用封装的阿里通义大模型，而不是自己的mcp-server
    @Bean
    public ChatClient getChatClient(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider) {
        log.debug("{}", toolCallbackProvider);
        return builder.defaultSystem("你是一个通过城市名称获取天气的系统")
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
}
