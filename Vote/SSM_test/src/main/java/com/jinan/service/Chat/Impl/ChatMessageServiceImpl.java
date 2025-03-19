package com.jinan.service.Chat.Impl;


import com.jinan.entities.Chat.chatMessage;
import com.jinan.mapper.Chat.ChatRecordMapper;
import com.jinan.service.Chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author CH
 * @title ChatMessageServiceImpl
 * @date 2024/11/22 19:46
 * @description TODO
 */
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    ChatRecordMapper chatRecordMapper;

    @Override
    public void AddMessage(chatMessage chat) {
        chatRecordMapper.AddMessage(chat);
    }

    @Override
    public List<chatMessage> selectMessage() {
        return chatRecordMapper.selectMessage();
    }
}
