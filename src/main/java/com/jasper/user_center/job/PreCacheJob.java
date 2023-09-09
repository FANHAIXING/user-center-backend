package com.jasper.user_center.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasper.user_center.mapper.UserMapper;
import com.jasper.user_center.model.domain.User;
import com.jasper.user_center.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    //重点用户
    List<Long> mainUserList = Arrays.asList(1L, 2l, 10l);

    //每天执行，预热推荐用户
    @Scheduled(cron = "0 49 15 * * *")
    public void doCacheRecommendUser() {
        RLock lock = redissonClient.getLock("Jasper:precachejob:docache:lock");
        try {
            //只有一个线程能获取锁
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)) {
                for (Long userId : mainUserList) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
                    String redisKey = String.format("Jasper:user:recommend:%s", userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    //写缓存
                    try {
                        valueOperations.set(redisKey, userPage, 10000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("Redis set key error", e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error", e);
        } finally {
            //只能释放自己加的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


}
