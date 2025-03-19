package cn.jinan;


import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author CH
 * @title SocketProcess
 * @date 2024/12/3 20:37
 * @description TODO
 */
public class SocketProcess implements Runnable {
    private Socket socket;
    private Tomcat tomcat;

    public SocketProcess(Socket socket,Tomcat tomcat) {
        this.socket = socket;
        this.tomcat = tomcat;
    }

    @Override
    public void run() {
        ProcessSocket(socket);
    }

    private void ProcessSocket(Socket socket){
        try {
            InputStream inputStream = socket.getInputStream();      // 要判断是否超过1kb 是否需要重复读
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);

            // 解析字节流，遇到一个空格就退出
            int pos = 0;
            int begin = 0, end = 0;
            for (; pos < bytes.length; pos++, end++) {
                if (bytes[pos] == ' ') break;
            }

            // 组合空格之前的字节流，转换成字符串就是请求方法
            StringBuilder method = new StringBuilder();
            for (; begin < end; begin++) {
                method.append((char) bytes[begin]);
            }

            pos++;
            begin++;
            end++;
            for (; pos < bytes.length; pos++, end++) {
                if (bytes[pos] == ' ') break;
            }
            StringBuilder url = new StringBuilder();
            for (; begin < end; begin++) {
                url.append((char) bytes[begin]);
            }

            // 解析协议版本
            pos++;
            begin++;
            end++;
            for (; pos < bytes.length; pos++, end++) {
                if (bytes[pos] == '\r') break;
            }
            StringBuilder protocl = new StringBuilder();
            for (; begin < end; begin++) {
                protocl.append((char) bytes[begin]);
            }
            Request request = new Request(method.toString(), url.toString(), protocl.toString(),socket);
            Response response = new Response(request);
            String requestUrl = request.getRequestURL().toString();
            requestUrl = requestUrl.substring(1);
            String[] parts = requestUrl.split("/");

            String appName = parts[0];
            Context context = tomcat.getContextMap().get(appName);

            if (parts.length > 1) {
                Servlet servlet = context.getByUrlPattern(parts[1]);

                if (servlet != null) {
                    servlet.service(request, response);
                    // 发送响应
                    response.complete();
                } else {
                    System.out.println("代码报错");
                    DefaultServlet defaultServlet = new DefaultServlet();
                    defaultServlet.service(request, response);
                    // 发送响应
                    response.complete();
                }
            }
//            MyServlet myServlet = new MyServlet();
//            myServlet.service(request,response);
//            response.complete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
