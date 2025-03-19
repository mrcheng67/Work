package jinan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIO {
    public static void main(String[] args) throws IOException {
        ServerSocket serviceSocket = new ServerSocket(8090);
        while(true){
            System.out.println("等待连接中。。。");
            // 阻塞方法
            Socket clientScoket = serviceSocket.accept();
            System.out.println("客户端连接成功。。。");
            // 异步的方式 ，可以 new Thread 处理这个
            ServiceHandler(clientScoket);
        }
    }

    private static void ServiceHandler(Socket clientScoket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("等待读取数据。。。");
        int read = clientScoket.getInputStream().read(bytes);
        System.out.println("读取数据完毕。。。");
        if(read != -1){
            System.out.println("接收到客户端的数据: " + new String(bytes,0,read));
            PrintWriter out = new PrintWriter(clientScoket.getOutputStream(), true);

            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length: " + read + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    bytes;

            out.println("我收到了你发送的数据。。。" + httpResponse);
        }
        System.out.println("发送数据完毕。。。");
    }
}