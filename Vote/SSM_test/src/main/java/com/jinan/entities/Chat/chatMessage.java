package com.jinan.entities.Chat;
import java.util.Date;

public class chatMessage<T> {
    private Integer id;
    private Integer from;
    private Integer to;
    private String message;
    private T CreateTime;

    @Override
    public String toString() {
        return "chatMessage{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                ", CreateTime=" + CreateTime +
                '}';
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreateTime(T createTime) {
        CreateTime = createTime;
    }

    public Integer getTo() {
        return to;
    }
    public Integer getId() {
        return id;
    }

    public Integer getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public T getCreateTime() {
        return CreateTime;
    }

}