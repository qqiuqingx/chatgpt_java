package com.example.javachatbot.service;

import com.unfbx.chatgpt.entity.chat.Message;

import java.util.List;
import java.util.Map;

public interface SendMsgService {

    public void sendMsg(List<Message> messages, Map<String, String> headers);
}
