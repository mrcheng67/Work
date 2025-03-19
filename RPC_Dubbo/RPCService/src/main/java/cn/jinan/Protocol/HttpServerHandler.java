package cn.jinan.Protocol;


import cn.jinan.Common.Invocation;
import cn.jinan.Register.LocalRegister;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author CH
 * @title HttpServerHandler
 * @date 2024/11/29 14:09
 * @description TODO
 */
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            Class classImpl = LocalRegister.get(interfaceName, "1.0");
            Method method = classImpl.getMethod(invocation.getMethodName(),invocation.getParameterTypes());
            String result = (String) method.invoke(classImpl.newInstance(),invocation.getParameters());

            IOUtils.write(result,resp.getOutputStream());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
