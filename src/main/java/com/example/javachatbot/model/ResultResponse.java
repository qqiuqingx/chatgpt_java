package com.example.javachatbot.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: laixiansong
 * @Description: http请求
 * @Vesion: 1.0.0
 * @Date: 2017/10/17
 */
@Data
public class ResultResponse<T> {

    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public void setData(T data) {
        this.data = data;
    }
    public ResultResponse setStatus(Integer code ,String msg){
        this.code = code;
        this.msg = msg;
        return this;
    }
    public String getMsg() {
        return msg;
    }
}