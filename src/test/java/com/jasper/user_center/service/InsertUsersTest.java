package com.jasper.user_center.service;

import com.jasper.user_center.mapper.UserMapper;
import com.jasper.user_center.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InsertUsersTest {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**
     * 批量插入
     */
    @Test
    public void doInsertUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUMBER = 10000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUMBER; i++) {
            User user = new User();
            user.setUsername("假用户");
            user.setUserAccount("fakeyupi");
            user.setAvatarUrl("https://foruda.gitee.com/avatar/1676895072005747651/5187_lionsoul_1578914315.jpg!avatar200");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123");
            user.setEmail("123@qq.com");
            user.setUseStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("11111");
            user.setTags("[]");
            user.setProfile("");
            userList.add(user);
        }
        userService.saveBatch(userList,1000);
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }

}
