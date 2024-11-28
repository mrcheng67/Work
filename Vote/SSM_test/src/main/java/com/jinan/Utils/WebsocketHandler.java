package com.jinan.Utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinan.entities.Chat.chatMessage;
import com.jinan.entities.UserLogin.Login;
import com.jinan.mapper.Chat.ChatRecordMapper;
import com.jinan.service.Chat.ChatMessageService;
import com.jinan.service.UserLogin.LoginService;
import com.jinan.service.Voter.VoteService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * WebSocket 处理器
 *
 * @author CH
 */
@Slf4j
@Sharable
@Component
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    Identify identify;          // 识别身份
    @Autowired
    LoginService loginService;
    @Autowired
    ChatMessageService chatMessageService;
//    @Autowired
//    private TransactionTemplate transactionTemplate;        // 事务

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketHandler.class);

    // 使用 ChannelGroup 存储所有连接的 Channel
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 使用 ConcurrentHashMap 存储用户名和对应的 Channel
    public static final ConcurrentHashMap<String, Channel> userChannels = new ConcurrentHashMap<>();
    ReentrantLock lock = new ReentrantLock();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {   // 接收到消息后会执行
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            String request = textWebSocketFrame.text();
            if(!request.contains("token")){
                log.error(request + " 格式错误 ");
                ctx.channel().writeAndFlush("From Srvice : 格式错误");
            }
            JSONObject jsonObject = JSON.parseObject(request);      // 解析 JSON 字符串
            String token = jsonObject.getString("token");
            String To = jsonObject.getString("to").trim();
            String message = jsonObject.getString("message");
            if(token == null){
                log.error("token为空，请重新登录  ---  Received token: {} ,  channelId: {}", token, ctx.channel());
                ctx.channel().writeAndFlush("From Srvice : " + token + "不存在，是否登录？");
            }else {
                token = identify.GetName(token);                        // 获取用户名
            }

            if (message.contains("init") && To.contains("init")) {
                Channel channelId = ctx.channel();
                handleInitMessage(channelId, token);
                List<String> list = getOnlineUsers();           // 通过getOnlineUsers 或 userChannels 得到所有在线用户
                broadcastMessage(new TextWebSocketFrame("onlineUsers:" + list));        // 广播用户在线
                System.out.println(userChannels + " list = " + list);
            }
            else if(!token.isEmpty()){
                Channel targetChannel = userChannels.get(To);
                if (targetChannel != null) {
                    Login fromUser = loginService.findUserByName(token);
                    Login toUser =loginService.findUserByName(To);
                    lock.lock();        // 加锁，防止
                    chatMessage chat = new chatMessage();
                    chat.setFrom(fromUser.getId());
                    chat.setTo(toUser.getId());
                    chat.setMessage(message);
                    chat.setCreateTime(new Date());
                    chatMessageService.AddMessage(chat);
                    lock.unlock();
                    sendMessageToUser(To , token , message);       // 发送给特定用户

                } else {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("User " + To + " not found"));
                }
            }
             else {
                // 如果消息格式不符合预期，广播给所有客户端
                ctx.channel().writeAndFlush("From Srvice : " + token + "不存在，是否登录？");
            }
        } else {
            // 不接受文本以外的数据帧类型
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 假设客户端连接时发送的第一个消息是用户名
        ctx.channel().read();
        ctx.channel().writeAndFlush(new TextWebSocketFrame("Please send your username"));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.info("链接创建：{}", ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LOGGER.info("链接断开：{}", ctx.channel().remoteAddress());
        channels.remove(ctx.channel());

        String username = getUsername(ctx.channel());
        if (username != null) {
            userChannels.remove(username);
            broadcastMessage(new TextWebSocketFrame("onlineUsers:" + getOnlineUsers()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    // 获取 Channel 对应的用户名
    public String getUsername(Channel channel) {
        for (Map.Entry<String, Channel> entry : userChannels.entrySet()) {
            if (entry.getValue() == channel) {
                return entry.getKey();
            }
        }
        return null;
    }

    // 向特定用户发送消息
    public void sendMessageToUser(String To ,String token, String message) {
        CompletableFuture.runAsync(() -> {
            Channel targetChannel = userChannels.get(To);
            if (targetChannel != null) {
                targetChannel.writeAndFlush(new TextWebSocketFrame("From " + token + ": " + message));
            } else {
                LOGGER.info("User {} not found", token);
            }
        });
    }

    // 广播消息给所有客户端
    private void broadcastMessage(TextWebSocketFrame message) {
        channels.writeAndFlush(message);
    }

    // 通过 channelId 来找到 channel
//    public Channel findChannelByChannelId(String channelId) {
//        for (Channel channel : channels) {
//            if (channel.id().asShortText().equals(channelId)) {
//                return channel;
//            }
//        }
//        return null;
//    }

    // 获取所有在线用户列表
    public List<String> getOnlineUsers() {
        return new ArrayList<>(userChannels.keySet());
    }

    // 解析初始化消息，设置 token 和 channelId
    public void handleInitMessage(Channel channel, String token) {
        //CompletableFuture.runAsync(() -> {
            if (token != null && channel != null) {                 //  异步执行发送消息 ,不能异步，异步会导致用户查找不到
                // 处理 token 和 channelId，例如存储到数据库或内存中
                log.info("保存到Netty的Map数组中 token: {}, channelId: {}", token, channel);
                // 你可以在这里调用服务方法来处理 token 和 channelId
                userChannels.put(token, channel);
            }
        //});
    }
}