package cn.jinan.LoadBalance;


import cn.jinan.Common.URL;

import java.util.List;
import java.util.Random;

/**
 * @author CH
 * @title LoadBalance
 * @date 2024/12/2 19:33
 * @description TODO
 */
public class LoadBalance {
    public static URL random(List<URL> urls){
        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);
    }
}
