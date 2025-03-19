package cn.jinan.Register;


import java.util.HashMap;
import java.util.Map;

/**
 * @title LocalRegister
 * @date 2024/12/2 13:36
 * @author CH
 * @description TODO
 */
public class LocalRegister {
    private static Map<String, Class> map = new HashMap<>();

    public static void regist(String interfaceName, String version, Class implClass){
        map.put(interfaceName+version, implClass);
    }

    public static Class get(String interfaceName, String version) {
        return map.get(interfaceName+version);
    }
}
