package cn.jinan.Register;


import cn.jinan.Common.URL;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CH
 * @title RemoteRegister
 * @date 2024/12/2 18:58
 * @description TODO
 */
public class RemoteRegister {
    public static Map<String, List<URL>> map = new HashMap<>();

    public static void Register(String interfaceName, URL url){
        List<URL> list = map.get(interfaceName);
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);

        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        map = getFile();
        return map.get(interfaceName);
    }

    public static boolean saveFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/register.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String,List<URL>> getFile(){
        try {
            FileInputStream fileInputStream = new FileInputStream("/register.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
