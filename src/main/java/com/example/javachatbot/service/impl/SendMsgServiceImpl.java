package com.example.javachatbot.service.impl;

import com.example.javachatbot.config.Config;
import com.example.javachatbot.listener.OpenAISSEEventSourceListener;
import com.example.javachatbot.service.SendMsgService;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private OpenAiStreamClient openAiStreamClient;


    @Override
    public void sendMsg(List<Message> messages, Map<String, String> headers) {
        SseEmitter sseEmitter = Config.Ssemitter_MAP.get(headers.get("uid"));
        OpenAISSEEventSourceListener openAISSEEventSourceListener = new OpenAISSEEventSourceListener(sseEmitter);
        ChatCompletion completion = ChatCompletion
                .builder()
                .messages(messages)
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())//todo 模型设置为前端可选择
                .build();
        openAiStreamClient.streamChatCompletion(completion,openAISSEEventSourceListener);
    }
}
