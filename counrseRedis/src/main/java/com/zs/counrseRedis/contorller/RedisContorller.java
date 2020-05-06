package com.zs.counrseRedis.contorller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by cloud on 2020/4/1.
 */
@RestController
@RequestMapping("redis")
public class RedisContorller {

    @Autowired
    private StringRedisTemplate redisTemplate;



    @GetMapping("get_str_key_val/{key}")
    public String getStrVal(@PathVariable String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("set_str_key_val/{key}")
    public void setStrVal(@PathVariable String key, String val, Long timeout) {
        if (timeout == null) {
            redisTemplate.opsForValue().set(key, val);
        } else {
            redisTemplate.opsForValue().set(key, val, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * set是无序不可重复的
     *
     * @param key
     * @return
     */
    @GetMapping("get_set/{key}")
    public Object getSetVal(@PathVariable String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @GetMapping("add_set/{key}")
    public void setSetVal(@PathVariable String key, String[] vals) {
        redisTemplate.opsForSet().add(key, vals);
    }

    /**
     * Zset会按权重score排序
     *
     * @param key
     * @return
     */
    @GetMapping("get_zset/{key}")
    public Object getZSetVal(@PathVariable String key) {
        //等同于 redisTemplate.opsForZSet().range(key, 0, -1); -1表示为倒数第一个元素
        return redisTemplate.opsForZSet().range(key, 0, redisTemplate.opsForZSet().size(key));
    }

    @GetMapping("add_zset/{key}")
    public void setZSetVal(@PathVariable String key, String val, @RequestParam(defaultValue = "-1") Long score) {
        redisTemplate.opsForZSet().add(key, val, score);
    }



    /**
     * List 有点像队列queue,根据下标排序，可以重复
     *
     * @param key
     * @return
     */
    @GetMapping("get_list/{key}")
    public Object getListVal(@PathVariable String key) {
        //等同于redisTemplate.opsForList().range(key,0,-1); -1表示为倒数第一个元素
        return redisTemplate.opsForList().range(key,0,redisTemplate.opsForList().size(key));
    }

    @GetMapping("add_list/{key}")
    public void setListVal(@PathVariable String key, String val) {
       redisTemplate.opsForList().rightPush(key,val);
    }


    /**
     * Hash 有点像对象
     *
     * @param key
     * @return
     */
    @GetMapping("get_hash/{key}")
    public Object getHashVal(@PathVariable String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @GetMapping("add_hash/{key}")
    public void setHashVal(@PathVariable String key, Person val) {
        /*redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        Jackson2HashMapper mapper = new Jackson2HashMapper(true);*/

        Map<String, String> params = JSON.parseObject(JSON.toJSONString(val),
                new TypeReference<Map<String, String>>(){});



        redisTemplate.opsForHash().putAll(key,params);
    }

    @Getter
    @Setter
    public class Person {
        Integer id;
        String firstname;
        String lastname;
    }


}
