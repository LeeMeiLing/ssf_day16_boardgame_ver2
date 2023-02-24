package sg.edu.nus.iss.ssf_workshop16ver2.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class BoardGameRepo {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public Boolean insertGame(JsonObject json){  //return Boolean true if success false if fail ?

        redisTemplate.opsForValue().set(Integer.toString(json.getInt("gid")), json.toString()); // cannot pass json as value, must use json.toString() for serialization
        return true;

    }

    public Optional<String> findGameById(String gid){  

        String result = (String) redisTemplate.opsForValue().get(gid); 
        
        if (null != result){
            return Optional.of(result);
        } else{
            return Optional.empty();
        }

    }
}
