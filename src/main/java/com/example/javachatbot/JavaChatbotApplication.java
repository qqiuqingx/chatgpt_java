package com.example.javachatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@PropertySource(value = { "classpath:config/config.properties"})
@SpringBootApplication
public class JavaChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaChatbotApplication.class, args);
    }

}
