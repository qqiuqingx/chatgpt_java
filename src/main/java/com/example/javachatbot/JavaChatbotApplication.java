package com.example.javachatbot;

import com.example.javachatbot.config.Config;
import com.example.javachatbot.config.OpenAi;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.util.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@PropertySource(value = { "classpath:config/config.properties"})
@SpringBootApplication
@EnableCaching
public class JavaChatbotApplication {
    @Autowired
    private OpenAi openAi;
    public static void main(String[] args) {
        SpringApplication.run(JavaChatbotApplication.class, args);
    }
    @Bean
    public OpenAiStreamClient openAiStreamClient() {
        System.out.println("openAi.getApiKey():"+openAi.getApiKey());
        System.out.println("openAi.getPROXY_PORT():"+openAi.getPROXY_PORT());
        System.out.println("openAi.getPROXY_HOST():"+openAi.getPROXY_HOST());
        System.out.println("Config.API_KEYS:"+Config.API_KEYS);
        //本地开发需要配置代理地址
        Proxy proxy=Proxy.NO_PROXY;
        if ((!StringUtils.isEmpty(openAi.getPROXY_HOST()))&&null!=openAi.getPROXY_PORT()) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(openAi.getPROXY_HOST(), openAi.getPROXY_PORT()));
        }

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();

        // if (proxy!= null)
        return OpenAiStreamClient
                .builder()
                .apiHost("https://api.openai.com/")
                .apiKey(Config.API_KEYS)
                //自定义key使用策略 默认随机策略
                .keyStrategy(new KeyRandomStrategy())
                .okHttpClient(okHttpClient)
                .build();
    }
}
