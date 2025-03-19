package jinan;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author CH
 * @title NIO
 * @date 2024/11/28 21:55
 * @description TODO
 */
public class NIO {
    static List<SocketChannel> ChannelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8090));
        // 设置   serverSocketChannel 为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务器启动成功。。。");
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();     //  不会阻塞，一直轮询
            if(socketChannel != null){                      // 存在客户端进行链接
                System.out.println("存在客户端。。。");
                socketChannel.configureBlocking(false);     //设置为非阻塞
                ChannelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = ChannelList.iterator();
            while(iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer  byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式不会阻塞
                int len = sc.read(byteBuffer);
                if(len > 0){
                    System.out.println("接收到消息为: " + new String(byteBuffer.array(), 0, byteBuffer.limit(), "UTF-8"));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("客户端断开连接。。。");
                }
            }
        }

    }
}
