package cn.jinan.Car;


/**
 * @author CH
 * @title Audi
 * @date 2024/12/4 15:50
 * @description TODO
 */
public class Audi implements Car{
    @Override
    public void start() {
        System.out.println("---------------------- 启动奥迪车辆 ----------------------");
    }

    @Override
    public void drive() {
        System.out.println("---------------------- 驾驶奥迪车辆 ----------------------");
    }

    @Override
    public void end() {
        System.out.println("---------------------- 停止奥迪车辆 ----------------------");
    }
}
