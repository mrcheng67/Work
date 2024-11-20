package com.jinan.service.Chat;


import io.netty.channel.Channel;

/**
 * @author CH
 * @title WebSocketService
 * @date 2024/11/20 21:25
 * @description TODO
 */

public interface WebSocketService {
    void sendMessageToUser(String username, String message);

    void setUserName(String channelId, String username);

    //Channel findChannelByChannelId(String channelId);
}
