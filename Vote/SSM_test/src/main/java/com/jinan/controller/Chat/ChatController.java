//package com.jinan.controller.Chat;
//
//import com.jinan.Utils.WebsocketHandler;
//import com.jinan.service.Chat.WebSocketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.stereotype.Controller;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class ChatController {
//    @Autowired
//    private WebsocketHandler handler;
//
//    @Autowired
//    private WebSocketService webSocketService;
//
//    // 发送消息给特定用户
//    @PostMapping("/send-message")
//    public String sendMessage(@RequestBody Map<String, String> data) {
//        String toUsername = data.get("toUsername");
//        String message = data.get("message");
//        webSocketService.sendMessageToUser(toUsername, message);
//        return "Message sent successfully";
//    }
//
//    // 设置用户名
//    @PostMapping("/set-username")
//    public String setUsername(@RequestBody Map<String, String> data) {
//        String channelId = data.get("channelId");
//        String username = data.get("username");
//        webSocketService.setUserName(channelId, username);
//        return "Username set successfully";
//    }
//}
