package com.example.javachatbot.model;

import lombok.Data;

import java.util.List;

@Data
public class GPTRequestBody {
    private String model="gpt-3.5-turbo";


    private List<GptEntity> messages;

    private int temperature=1;
    private int max_tokens=2000;

    public GPTRequestBody(List<GptEntity> list) {

        this.messages = list;
    }

    public GPTRequestBody() {}

    public GPTRequestBody(String model,int temperature, int max_tokens,List<GptEntity> list) {
        this.model = model;

        this.temperature = temperature;
        this.max_tokens = max_tokens;
        this.messages=list;
    }

}
