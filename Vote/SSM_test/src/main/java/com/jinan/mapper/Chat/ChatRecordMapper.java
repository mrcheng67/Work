package com.jinan.mapper.Chat;

import com.jinan.entities.Chat.chatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author CH
 * @title ChatRecord
 * @date 2024/11/21 23:11
 * @description TODO
 */
@Mapper
public interface ChatRecordMapper {
    void AddMessage(chatMessage chat);

    List<chatMessage> selectMessage();
}
