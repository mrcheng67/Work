package jinan;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
/**
 * @author CH
 * @title NIOSelector
 * @date 2024/11/28 22:26
 * @description TODO
 */

import java.io.IOException;

public class NonBlockingServer {
    private static final List<SocketChannel> ChannelList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // 创建一个 Selector
        Selector selector = Selector.open();

        // 创建一个 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(12345));

        // 注册到 Selector 上，监听 ACCEPT 事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器启动，等待客户端连接...");

        while (true) {
            // 选择一组键，其对应的通道已经准备好进行 I/O 操作
            selector.select();

            // 获取已选择的键集
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove(); // 删除已处理的键，防止重复处理

                if (key.isAcceptable()) {
                    // 处理新的连接请求
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    if (socketChannel != null) {
                        System.out.println("添加客户端。。。");
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        synchronized (ChannelList) {
                            ChannelList.add(socketChannel);
                        }
                    }
                } else if (key.isReadable()) {
                    // 处理读取操作
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    try {
                        int len = sc.read(byteBuffer);
                        if (len > 0) {
                            byteBuffer.flip();
                            byte[] messageBytes = new byte[byteBuffer.limit()];
                            byteBuffer.get(messageBytes);
                            String message = new String(messageBytes, "UTF-8").trim();
                            System.out.println("接收到消息为: " + message);

                            byteBuffer.compact(); // 移动未读数据到缓冲区开头
                        } else if (len == -1) {
                            // 客户端断开连接
                            synchronized (ChannelList) {
                                ChannelList.remove(sc);
                            }
                            sc.close();
                            System.out.println("客户端断开连接。。。");
                        }
                    } catch (IOException e) {
                        // 客户端异常断开
                        synchronized (ChannelList) {
                            ChannelList.remove(sc);
                        }
                        sc.close();
                        System.out.println("客户端异常断开。。。");
                    }
                }
            }
        }
    }
}
//public class NonBlockingServer {
//    private static final List<SocketChannel> ChannelList = new ArrayList<>();
//
//    public static void main(String[] args) throws Exception {
//        // 创建一个 Selector
//        Selector selector = Selector.open();
//
//        // 创建一个 ServerSocketChannel
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.configureBlocking(false);
//        serverSocketChannel.socket().bind(new InetSocketAddress(12345));
//
//        // 注册到 Selector 上，监听 ACCEPT 事件
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//        System.out.println("服务器启动，等待客户端连接...");
//
//        while (true) {
//            // 选择一组键，其对应的通道已经准备好进行 I/O 操作
//            selector.select();
//
//            // 获取已选择的键集
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iterator = selectedKeys.iterator();
//
//            while (iterator.hasNext()) {
//                SelectionKey key = iterator.next();
//
//                if (key.isAcceptable()) {
//                    // 处理新的连接请求
//                    SocketChannel socketChannel = serverSocketChannel.accept();
//                    if (socketChannel != null) {
//                        System.out.println("添加客户端。。。");
//                        socketChannel.configureBlocking(false);
//                        socketChannel.register(selector, SelectionKey.OP_READ);
//                        ChannelList.add(socketChannel);
//                    }
//                } else if (key.isReadable()) {
//                    // 处理读取操作
//                    SocketChannel sc = (SocketChannel) key.channel();
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//                    // 非阻塞模式不会阻塞
//                    int len = sc.read(byteBuffer);
//                    if (len > 0) {
//                        // 翻转缓冲区，准备读取数据
//                        byteBuffer.flip();
//
//                        // 将字节数组转换为字符串，使用 UTF-8 编码
//                        String message = new String(byteBuffer.array(), 0, byteBuffer.limit());
//                        System.out.println("接收到消息为: " + message);
//
//                        // 清空缓冲区，准备下一次读取
//                        byteBuffer.clear();
//                    } else if (len == -1) {
//                        // 客户端断开连接
//                        iterator.remove();
//                        ChannelList.remove(sc);
//                        sc.close();
//                        System.out.println("客户端断开连接。。。");
//                    }
//                }
//
//                // 删除已处理的键
//                iterator.remove();
//            }
//        }
//    }
//}
