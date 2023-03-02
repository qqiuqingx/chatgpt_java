package com.example.javachatbot.utils;

import com.example.javachatbot.model.GptEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
//todo  缓存TTL的实现
public class CacheUtils {
   private static Map<String,List<GptEntity>> map=new HashMap();

    @CachePut(value = "GptSessionCache",key = "#key")
    public List<GptEntity> putCache(String key,GptEntity gpt){

        List<GptEntity>   gptEntities = map.get(key);
        if (CollectionUtils.isEmpty(gptEntities)){
            gptEntities=new ArrayList<>();
        }
        gptEntities.add(gpt);

        map.put(key,gptEntities);

        return gptEntities;
    }
    @Cacheable(value = "GptSessionCache",key = "#key")
    public  List<GptEntity> GptSessionCache(String key){
        return map.get(key);
    }

}
