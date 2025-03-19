package cn.jinan;


import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author CH
 * @title Tomcat
 * @date 2024/12/3 17:54
 * @description TODO
 */
public class Tomcat {
    private Integer port;
    public Tomcat(Integer port){
        this.port = port;
    }

    private static Map<String,Context> contextMap = new HashMap<>();
    
    public static void main(String[] args) {
        Tomcat t = new Tomcat(8090);
        t.deploy();
        t.start();
    }

    public void start(){
        // socket 链接 tcp
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                executorService.execute(new SocketProcess(socket,this));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deploy() {
        File webapps = new File(new File(System.getProperty("user.dir"),"Tomcat_Demo"),"webapps");
        for(String app : webapps.list()){
            deploys(app,webapps);
        }
    }

    private void deploys(String appName,File webapps) {
        Context context = new Context(appName);
        File appDir = new File(webapps,appName);
        File classDir = new File(appDir,"classes");
        List<File> allFile = getAllFilePath(classDir);          // 保存所有文件

        for (File clazz : allFile) {
            try {
                String name = clazz.getPath();
                name = name.replace(classDir.getPath() + "\\", "");
                name = name.replace(".class", "");
                name = name.replace("\\", ".");

                WebAppClassLoader classLoader = new WebAppClassLoader(new URL[]{classDir.toURL()});
                Class<?> servletClass = classLoader.loadClass(name);            // 自定义的加载器去加载

                if (HttpServlet.class.isAssignableFrom(servletClass)) {
                    if (servletClass.isAnnotationPresent(WebServlet.class)) {
                        WebServlet annotation = servletClass.getAnnotation(WebServlet.class);
                        String[] urlPatterns = annotation.urlPatterns();

                        for (String urlPattern : urlPatterns) {
                            context.addUrlPatternMapping(urlPattern, (Servlet) servletClass.newInstance());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        contextMap.put(appName,context);
    }

    private List<File> getAllFilePath(File classDir) {
        List<File> AllPath = new ArrayList<>();
        File[] files = classDir.listFiles();
        if(files != null){
            for(File file : files){
                if(file.isDirectory()){
                    AllPath.addAll(getAllFilePath(file));
                }else {
                    AllPath.add(file);
                }
            }
        }
        return AllPath;
    }

    public static Map<String, Context> getContextMap() {
        return contextMap;
    }
}
