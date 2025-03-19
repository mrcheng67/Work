package cn.jinan;


import cn.jinan.Common.Invocation;
import cn.jinan.Protocol.HttpClient;
import cn.jinan.ProxyFactory.ProxyInstance;

import java.io.IOException;

/**
 * @author CH
 * @title Consumer
 * @date 2024/12/2 15:41
 * @description TODO
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        Travel travel = ProxyInstance.getProxy(Travel.class);
        String result = travel.go("CHH1");
        System.out.println("result = " + result);
    }
}
