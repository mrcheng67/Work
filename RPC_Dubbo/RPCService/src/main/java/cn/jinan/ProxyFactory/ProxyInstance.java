package cn.jinan.ProxyFactory;


import cn.jinan.Common.Invocation;
import cn.jinan.Common.URL;
import cn.jinan.LoadBalance.LoadBalance;
import cn.jinan.Protocol.HttpClient;
import cn.jinan.Register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author CH
 * @title ProxyInstance
 * @date 2024/12/2 16:06
 * @description TODO
 */
public class ProxyInstance {
    public static  <T> T getProxy(Class InterfaceClass){
        Object ProxyInstance = Proxy.newProxyInstance(InterfaceClass.getClassLoader(), new Class[]{InterfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(InterfaceClass.getName(),
                        method.getName(), method.getParameterTypes(), args);

                HttpClient client = new HttpClient();

                List<URL> lists = RemoteRegister.get(InterfaceClass.getName());

                URL url = LoadBalance.random(lists);

                String result = client.send(url.getHostname(),url.getPort(),invocation);

                return result;
            }
        });
        return (T) ProxyInstance;
    }
}
