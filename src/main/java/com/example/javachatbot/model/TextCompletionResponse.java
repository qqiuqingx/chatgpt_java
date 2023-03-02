package com.example.javachatbot.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;
@Data
public class TextCompletionResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choice> choices;
    private Usage usage;


    @Data
    public static class Choice {

        private int index;
        private GptEntity message;
        private String finish_reason;


    }
@Data
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;


    }


}
