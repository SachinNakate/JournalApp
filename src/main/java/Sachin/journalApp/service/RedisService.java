package Sachin.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    // following is get method
    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            // now we have to convert this object into pojo class
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);

        }catch (Exception e){
            log.error("Exception"+ e);
            return null;
        }

    }

    public void set(String key, Object o, Long ttl){ // ttl = time to leave
        // we set a Object corresponding to a key for a particular amt of time
        try {
            ObjectMapper objectMapper= new ObjectMapper();
            // converting object into json
            String jsonValue = objectMapper.writeValueAsString(o);

            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);

        }catch (Exception e){
            log.error("Exception"+ e);
        }

    }


}
