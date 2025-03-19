package cn.jinan;


/**
 * @author CH
 * @title Traveling
 * @date 2024/12/4 23:10
 * @description TODO
 */
public class Traveling implements Travel {
    @Override
    public String go(String name) {
        System.out.println("现在出发" + name);
        return "出发" + name;
    }
}
