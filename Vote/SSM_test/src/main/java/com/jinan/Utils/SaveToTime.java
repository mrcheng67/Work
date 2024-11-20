package com.jinan.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinan.Configuration.DelayedTask;
import com.jinan.entities.Voter.Vote;
import com.jinan.service.Voter.VoteService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.DelayQueue;

/**
 * 添加学习记录处理器工具类
 * 1) 处理缓存
 * 2)
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SaveToTime {
    private final StringRedisTemplate redisTemplate;
    private final VoteService voteService;
    // 缓存前缀
    private static final String LEARNING_RECORD_CACHE_PREFIX = "cheng:";
    private DelayQueue<DelayedTask<Vote>> queue = new DelayQueue<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        System.out.println("主线程...对象创建了");
        // 启动时开启一个子线程,主线程不会阻塞
        CompletableFuture.runAsync(() -> {
            this.dealData();
        });
    }

    @PreDestroy
    public void destroy() {
        System.out.println("对象销毁了");
    }

    public void dealData() {
        try {
            // 获取延时消息
            DelayedTask<Vote> task = queue.take();
            Vote data = task.getData();
            log.info("延时队列获取到了消息");
            // 获取redis 中的消息
            Vote RedisVote = readRecordCache(data.getVoteId());
            /*     比较消息    如果消息不一致,证明: 用户在持续播放, 抛弃消息
             */
            if (!data.getCreatedAt().equals(RedisVote.getCreatedAt())) {
                log.info("redis 中的数据和 延时消息不一致... 不处理");
                return;
            }
            log.info("redis 中的数据和 延时消息一致... 处理..写入数据库");
            // 更新 vote
            data.setIsActive(false);
            voteService.updateVote(data);
            removeRecordCache(data.getVoteId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 写入消息到队列
    public void writeRecord2Queue(Vote vote) {
        log.info("延时队列放入了消息:");
        queue.add(new DelayedTask<>(vote, Duration.ofSeconds(vote.getExpiryTime())));
    }

    // 写入缓存
    public void writeRecordCache(Vote vote){
        log.info("缓存放入了消息:");
        // 1. 转成json 字符串
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(vote);
        } catch (Exception e) {
            log.error("序列化失败", e);
            return;
        }
        // 2. 拼接key  cheng : id
        String key = LEARNING_RECORD_CACHE_PREFIX + vote.getVoteId();
        // 3. 存储缓存(覆盖)
        redisTemplate.opsForValue().set(key, jsonData);
        // 4. 设置超时时间
        redisTemplate.expire(key, Duration.ofSeconds(vote.getExpiryTime()));
    }

    // 读取缓存
    public Vote readRecordCache(Integer voteId) {
        // 1. 拼接key  cheng : id
        String key = LEARNING_RECORD_CACHE_PREFIX + voteId;
        // 真正的是String
        String jsonData = redisTemplate.opsForValue().get(key);
        if (jsonData == null) {
            return null;
        }
        // 转成对象
        try {
            return objectMapper.readValue(jsonData, Vote.class);
        } catch (Exception e) {
            log.error("反序列化失败", e);
            return null;
        }
    }

    // 删除缓存
    public void removeRecordCache(Integer voteId) {
        // 1. 拼接key  cheng : id
        String key = LEARNING_RECORD_CACHE_PREFIX + voteId;
        redisTemplate.delete(key);
    }
}


