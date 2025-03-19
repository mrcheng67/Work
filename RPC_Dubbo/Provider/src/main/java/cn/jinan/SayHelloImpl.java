package cn.jinan;


/**
 * @author CH
 * @title SayHelloImpl
 * @date 2024/12/2 15:28
 * @description TODO
 */
public class SayHelloImpl implements SayHello{

    @Override
    public String Say(String name) {
        System.out.println("Hello," + name);
        return "Hello,"+name;
    }
}
