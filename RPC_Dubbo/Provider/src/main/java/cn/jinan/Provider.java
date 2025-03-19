package cn.jinan;

import cn.jinan.Common.URL;
import cn.jinan.Protocol.HttpServer;
import cn.jinan.Register.LocalRegister;
import cn.jinan.Register.RemoteRegister;

/**
 * @author CH
 * @title Provider
 * @date 2024/11/28 23:05
 * @description TODO
 */
public class Provider {
    public static void main(String[] args) {
        // 动态注册
        URL url = new URL("localhost",8090);
        RemoteRegister.Register(SayHello.class.getName(),url);

        LocalRegister.regist(SayHello.class.getName(),"1.0",SayHelloImpl.class);

        RemoteRegister.Register(Travel.class.getName(),url);

        LocalRegister.regist(Travel.class.getName(),"1.0",Traveling.class);
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(),url.getPort());
    }
}
