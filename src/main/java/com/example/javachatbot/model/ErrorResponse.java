package com.example.javachatbot.model;

import lombok.Data;

@Data
public class ErrorResponse {
    public ErrorDetails error;
    @Data
    public static class ErrorDetails {
        public String message;
        public String type;
        public String param;
        public String code;
    }
}
