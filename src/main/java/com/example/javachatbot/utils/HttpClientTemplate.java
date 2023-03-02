package com.example.javachatbot.utils;

import com.example.javachatbot.config.Config;
import com.example.javachatbot.config.UserCode;
import com.example.javachatbot.model.ErrorResponse;
import com.example.javachatbot.model.GPTRequestBody;
import com.example.javachatbot.model.GptEntity;
import com.example.javachatbot.model.TextCompletionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
@Component
public class HttpClientTemplate {

    @Autowired
    CacheUtils cache;



    // 创建 RequestConfig 实例，设置代理服务器信息
    private static RequestConfig config ;
    static {
        if (StringUtils.hasText(Config.proxy_host)&&null!=Config.proxy_port){
        config = RequestConfig.custom()
                .setProxy(new HttpHost(Config.proxy_host, Config.proxy_port))
                .build();
        }
    }



    public String ipAddress(HttpServletRequest request)  {
        // 接收request
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }




    public  String sendPostRequest(String url, GPTRequestBody require,String cacheKey) throws IOException {

//        GptEntity message=new GptEntity("test1", UserCode.ASSISTANT.getRole());
//        cache.putCache(cacheKey,message);
//        return message.getContent();
        HttpClientBuilder builder = HttpClientBuilder.create();
        if (config != null){
            builder.setDefaultRequestConfig(config);
        }


        CloseableHttpClient httpClient = builder.build();
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头参数
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("Authorization", "Bearer "+Config.API_KEY));
        httpPost.setHeaders(headers.toArray(new Header[0]));
        ObjectMapper mapper = new ObjectMapper();
        String requestBodyJson = mapper.writeValueAsString(require);
        // 将JSON字符串设置为请求体
        StringEntity requestEntity = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String jsonString=   EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

            if (statusCode == 200) {
                TextCompletionResponse textCompletionResponse = mapper.readValue(jsonString, TextCompletionResponse.class);
                GptEntity message = textCompletionResponse.getChoices().get(0).getMessage();
                cache.putCache(cacheKey,message);
                return message.getContent();
            }else {
                ErrorResponse errorResponse = mapper.readValue(jsonString, ErrorResponse.class);
                return errorResponse.getError().getMessage();
            }
        } finally {
            response.close();
            httpClient.close();
        }

    }
}
