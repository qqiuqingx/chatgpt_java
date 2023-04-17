package com.example.javachatbot.controller;

import com.example.javachatbot.config.Config;
import com.example.javachatbot.model.GptEntity;
import com.example.javachatbot.service.SendMsgService;
import com.unfbx.chatgpt.entity.chat.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("/Stream")
public class StreamAskController {

    @Autowired
    private SendMsgService sendMsgService;

    @GetMapping(value = "/createStream")

    public SseEmitter createStream(@RequestHeader Map<String, String> headers, HttpServletRequest request) throws IOException {

        //todo session中获取
        String uid = headers.get("uid");
        if (StringUtils.isEmpty(uid)) {
            return null;
        }

        if (Config.Ssemitter_MAP.containsKey(uid)) {
            return Config.Ssemitter_MAP.get(uid);
        }
        SseEmitter emitter = new SseEmitter();
        Config.Ssemitter_MAP.put(uid, emitter);
        return emitter;
    }

    @RequestMapping(value = "/Stream/ask", method = RequestMethod.POST)
    @ResponseBody
    public GptEntity getSteam(@RequestBody List<GptEntity> messages, @RequestHeader Map<String, String> headers) throws IOException {
        try {
            System.out.println(messages);
            List<Message> newMessages=new ArrayList<>();
            for (GptEntity message:messages) {
                Message newMessage=new Message();
                BeanUtils.copyProperties(message,newMessage);
                newMessages.add(newMessage);
            };
            System.out.println(newMessages);
            sendMsgService.sendMsg(newMessages, headers);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
