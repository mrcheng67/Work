package com.jinan.Utils;

import com.jinan.service.Voter.VoteService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
/**
 *
 * @author CH
 *
 */

@Slf4j
@Sharable
@Component
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketHandler.class);

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Autowired
    VoteService voteService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            // 业务层处理数据
            this.voteService.getVoteById(1);
            log.info("数据处理到了 WebsocketHandler " + ctx + "  msg = " + msg);
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + ((TextWebSocketFrame) msg).text() + System.currentTimeMillis()));
            broadcastMessage("广播消息: " + textWebSocketFrame.text());
        } else {
            // 不接受文本以外的数据帧类型
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

//    private void sendToUser(String userId, String message) {
//        Channel userChannel = Login.getUserChannel(userId);
//        if (userChannel != null && userChannel.isActive()) {
//            userChannel.writeAndFlush(new TextWebSocketFrame(message));
//        } else {
//            log.warn("User {} not found or channel inactive", userId);
//        }
//    }
    private void broadcastMessage(String message) {
        channels.writeAndFlush(new TextWebSocketFrame(message));
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LOGGER.info("链接断开：{}", ctx.channel().remoteAddress());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.info("链接创建：{}", ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }
}