//package com.jinan.service.Chat.Impl;
//
//
//import com.jinan.Utils.WebsocketHandler;
//import com.jinan.service.Chat.WebSocketService;
//import io.netty.channel.Channel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @author CH
// * @title WebSocketServiceImpl
// * @date 2024/11/20 21:32
// * @description TODO
// */
//@Service
//public class WebSocketServiceImpl implements WebSocketService {
//
//    @Autowired
//    WebsocketHandler websocketHandler;
//
//    @Override    // 向特定用户发送消息
//    public void sendMessageToUser(String username, String message) {
//        websocketHandler.sendMessageToUser(username, message);
//    }
//
//    @Override    // 设置用户名
//    public void setUserName(String channelId, String username) {
//        Channel channel = findChannelByChannelId(channelId);    // 假设你有一个方法可以找到对应的 Channel
//        if (channel != null) {
//            websocketHandler.setUserName(channel, username);
//        }
//    }
//
//    // 根据 Channel ID 查找 Channel
//    private Channel findChannelByChannelId(String channelId) {
//        // 实现查找 Channel 的逻辑
//        // 这里假设你有一个方法可以找到对应的 Channel
//        return null; // 示例中返回 null
//    }
//}
