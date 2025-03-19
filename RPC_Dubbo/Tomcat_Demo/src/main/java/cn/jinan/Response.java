package cn.jinan;


import cn.jinan.Abstract.MyHttpServletResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CH
 * @title Response
 * @date 2024/12/3 21:52
 * @description TODO
 */
public class Response extends MyHttpServletResponse {
    private byte SP = ' ';
    private byte CR = '\r';
    private byte LF = '\n';
    private Integer status = 200;
    private String message = "OK";
    private Map<String,String> headers = new HashMap<>();
    private Request request;
    private OutputStream outputStream;
    private ResponseServletOutputStream responseServletOutputStream = new ResponseServletOutputStream();

    public Response(Request request) {
        try {
            this.request = request;
            this.outputStream = request.getSocket().getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseServletOutputStream getOutputStream() throws IOException {
        return responseServletOutputStream;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status,String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public void addHeader(String s, String s1) {
        headers.put(s,s1);
    }
    public void complete(){
        try {
            sendResponseLine();
            sendResponseHeader();
            sendResponseBody();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponseLine() throws IOException {
        outputStream.write(request.getProtocol().getBytes());
        outputStream.write(SP);
        outputStream.write(status);
        outputStream.write(SP);
        outputStream.write(message.getBytes());
        outputStream.write(CR);
        outputStream.write(LF);
    }

    private void sendResponseHeader() throws IOException {
        if (!headers.containsKey("Content-Length")) {
            addHeader("Content-Length", String.valueOf(getOutputStream().getPos()));
        }

        if (!headers.containsKey("Content-Type")) {
            addHeader("Content-Type", "text/plain;charset=utf-8");
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            outputStream.write(key.getBytes());
            outputStream.write(":".getBytes());
            outputStream.write(value.getBytes());
            outputStream.write(CR);
            outputStream.write(LF);
        }
        outputStream.write(CR);
        outputStream.write(LF);
    }

    private void sendResponseBody() throws IOException {
        outputStream.write(getOutputStream().getBytes());
    }
}
