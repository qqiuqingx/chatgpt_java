package com.example.javachatbot.config;



import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component

@Data
public class Config {


    public static Map<String, SseEmitter> Ssemitter_MAP = new ConcurrentHashMap<>();
    public static   String API_KEY;


    public static String proxy_host;

    public static List<String>  API_KEYS;
    public static Integer proxy_port;

    public String getAPI_KEY() {
        return API_KEY;
    }
    @Value("${apikey}")
    public void setAPI_KEYS(List<String> API_KEYS) {
        Config.API_KEYS = API_KEYS;
    };
    @Value("${apikey}")
    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getProxy_host() {
        return proxy_host;
    }
    @Value("${host}")
    public void setProxy_host(String proxy_host) {
        this.proxy_host = proxy_host;
    }

    public int getProxy_port() {
        return proxy_port;
    }
    @Value("${port}")
    public void setProxy_port(Integer proxy_port) {
        this.proxy_port = proxy_port;
    }
}
