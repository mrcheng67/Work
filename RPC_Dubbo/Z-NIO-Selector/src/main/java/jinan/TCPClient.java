package jinan;


import java.io.OutputStream;
import java.net.Socket;

/**
 * @author CH
 * @title send
 * @date 2025/3/13 20:50
 * @description TODO
 */
// 服务器端修改为TCP（保持原代码，仅需修改客户端）
public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8090);
        OutputStream os = socket.getOutputStream();
        os.write("Hello from Java!".getBytes());
        os.flush();
        socket.close();
    }
}