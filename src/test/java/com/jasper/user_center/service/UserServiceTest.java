package com.jasper.user_center.service;

import com.jasper.user_center.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testsSearchUsersByTags() {
        List<String> tagNameList = List.of(new String[]{"java", "python"});
        List<User> userList = userService.searchUsersByTags(tagNameList);
        Assert.assertNotNull(userList);

    }
}