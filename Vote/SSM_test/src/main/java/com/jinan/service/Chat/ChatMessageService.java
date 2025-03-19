package com.jinan.service.Chat;

import com.jinan.entities.Chat.chatMessage;

import java.util.Date;
import java.util.List;

/**
 * @author CH
 * @title ChatMessageService
 * @date 2024/11/22 19:45
 * @description TODO
 */
public interface ChatMessageService {
    void AddMessage(chatMessage chat);

    List<chatMessage> selectMessage();
}
