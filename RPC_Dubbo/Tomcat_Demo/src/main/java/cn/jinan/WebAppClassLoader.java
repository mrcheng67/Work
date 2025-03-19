package cn.jinan;


import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author CH
 * @title WebAppClassLoader
 * @date 2024/12/4 18:32
 * @description TODO
 */
public class WebAppClassLoader extends URLClassLoader {
    public WebAppClassLoader(URL[] urls) {
        super(urls);
    }
}
