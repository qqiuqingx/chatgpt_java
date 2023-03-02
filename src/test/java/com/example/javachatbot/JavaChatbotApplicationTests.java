package com.example.javachatbot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaChatbotApplicationTests {
    @Value("${GPT2_APIKEY}")
    private static String API_Key;
    @Test
    void contextLoads() {


        System.out.println(API_Key);
    }

}
