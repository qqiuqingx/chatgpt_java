package com.example.javachatbot.controller;

import com.example.javachatbot.config.Config;
import com.example.javachatbot.config.UserCode;
import com.example.javachatbot.model.GPTRequestBody;
import com.example.javachatbot.model.GptEntity;
import com.example.javachatbot.model.TextCompletionResponse;
import com.example.javachatbot.utils.CacheUtils;
import com.example.javachatbot.utils.HttpClientTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class askController {
    @Autowired
    CacheUtils cache;
    @Autowired
    HttpClientTemplate httpClientTemplate;
    private static String URL="https://api.openai.com/v1/chat/completions";
    @RequestMapping(value = "/ask", method = RequestMethod.POST)
    @ResponseBody
    public GptEntity getBuAll(String ask, HttpServletRequest request) throws IOException {

        String s = httpClientTemplate.ipAddress(request);
        System.out.println("ip:"+s);
        if (!StringUtils.hasText(s)||!StringUtils.hasText(ask)){
            return  new GptEntity("请稍候。。。",UserCode.ASSISTANT.getRole());
        }

         cache.putCache(s, new GptEntity(ask, UserCode.USER.getRole()));

        List<GptEntity> test = cache.GptSessionCache(s);
        GPTRequestBody require = new GPTRequestBody(test );
        String message = httpClientTemplate.sendPostRequest(URL, require, s);

        return  new GptEntity(message,UserCode.ASSISTANT.getRole());
    }






    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public  List<GptEntity> getAll(HttpServletRequest request)   {

        String s = httpClientTemplate.ipAddress(request);
        System.out.println("ip:"+s);
        if (!StringUtils.hasText(s)){
            return  new ArrayList<>();
        }

        return  cache.GptSessionCache(s);
    }


}
