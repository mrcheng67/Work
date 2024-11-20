package com.jinan.entities.Chat;


public class chatMessage<T> {
    private Long id;
    private String userName;
    private String message;
    private T CreateTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreateTime(T createTime) {
        CreateTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public T getCreateTime() {
        return CreateTime;
    }

    @Override
    public String toString() {
        return "chatMessage{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", CreateTime=" + CreateTime +
                '}';
    }
}