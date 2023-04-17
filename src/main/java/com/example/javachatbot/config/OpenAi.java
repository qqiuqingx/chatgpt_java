package com.example.javachatbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="openai",ignoreUnknownFields = false)
@Configuration
@Component

@PropertySource("classpath:config/config.properties")
@Data
public class OpenAi {
    private String apiKey;
    private String PROXY_HOST;
    private Integer PROXY_PORT;




}
