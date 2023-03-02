package com.example.javachatbot.config;

public enum UserCode {
    USER(0,"user"),
    ASSISTANT(1,"assistant"),

     ;
    private final int code;
    private final String role;
    UserCode(int code, String desc){
        this.code = code;
        this.role = desc;
    }
    public int getCode() {
        return code;
    }
    public String getRole() {
        return role;
    }
}