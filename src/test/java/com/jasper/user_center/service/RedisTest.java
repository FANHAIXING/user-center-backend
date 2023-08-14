package com.jasper.user_center.service;

import com.jasper.user_center.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, java.io.Serializable> redisTemplate;

    @Test
    void test(){
        ValueOperations<String, java.io.Serializable> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("yupiString","dog");
        valueOperations.set("yupiInt",1);
        valueOperations.set("yupiDouble",2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("yupipipi");
        valueOperations.set("yupiUser",user);

        //查
        Object yupiString = valueOperations.get("yupiString");
        Assertions.assertTrue("dog".equals((String)yupiString));

        Object yupiInt = valueOperations.get("yupiInt");
        Assertions.assertEquals((Integer) yupiInt, 1);

        Object yupiDouble = valueOperations.get("yupiDouble");
        Assertions.assertEquals(2.0, (Double) yupiDouble);

        System.out.println(valueOperations.get("yupiUser"));

        //删
        redisTemplate.delete("yupiInt");
    }
}
