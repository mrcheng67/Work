package cn.jinan;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;
/**
 * @title Context
 * @date 2024/12/4 18:40
 * @author CH
 * @description TODO
 */

public class Context {

    private String appName;
    private Map<String, Servlet> urlPatternMapping = new HashMap<>();

    public Context(String appName) {
        this.appName = appName;
    }

    public void addUrlPatternMapping(String urlPattern, Servlet servlet) {
        urlPatternMapping.put(urlPattern, servlet);
    }

    public Servlet getByUrlPattern(String urlPattern) {
        for (String key : urlPatternMapping.keySet()) {
            if (key.contains(urlPattern)) {
                return urlPatternMapping.get(key);
            }
        }
        return null;
    }
}
