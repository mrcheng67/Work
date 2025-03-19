package com.jinan.Configuration;

import com.jinan.entities.Voter.Vote;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
@Data
@Slf4j
public class DelayedTask<T> implements Delayed {

    private Vote data;// 数据
    private long deadLineTime;// 当前消息什么时候到期

    // Duration jdk 提供的一个可以表示指定时间单位 时间的 对象
    public DelayedTask(Vote data, Duration duration) {
        this.data = data;
        deadLineTime = System.currentTimeMillis() + duration.toMillis();
    }

    /**
     * DelayQueue 的take() 方法 会不断调用   本方法,获取消息的剩余时间
     *  1) 消息剩余时间<=0 表示消息到期
     *  2) >0 表示消息未到期
     */
    @Override
    public long getDelay(TimeUnit unit) {
        log.info("getDelay方法被调用了");
        //return  deadLineTime-System.currentTimeMillis();
        return unit.convert(deadLineTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }
    /**
     * 如果有多个消息,则 底层存储消息时会调用该方法进行排序
     * 返回值:
     *    如果>0  表示当前任务 到期时间 长,应该放入队列的后面
     *    如果<=0  表示当前任务 到期时间 断,应该放入队列的前面
     */
    @Override
    public int compareTo(Delayed other) {
        // 获取当前任务时间和其他任务时间的 差
        Long i = this.getDelay(TimeUnit.SECONDS) - other.getDelay(TimeUnit.SECONDS);
        return i.intValue();
    }
}
