package cn.jinan;


import cn.jinan.Abstract.MyHttpServletRequest;

import java.net.Socket;

/**
 * @author CH
 * @title Request
 * @date 2024/12/3 20:51
 * @description TODO
 */
public class Request extends MyHttpServletRequest {
    private String method;
    private String url;
    private String protocl;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public String getMethod() {
        return method;
    }

    public StringBuffer getRequestURL() {
        return new StringBuffer(url);
    }

    public String getProtocol() {
        return protocl;
    }

    public Request(String method, String url, String protocl,Socket socket) {
        this.method = method;
        this.url = url;
        this.protocl = protocl;
        this.socket = socket;
    }
}
