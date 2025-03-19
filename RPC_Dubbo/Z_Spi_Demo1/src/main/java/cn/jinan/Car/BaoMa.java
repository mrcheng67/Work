package cn.jinan.Car;


/**
 * @author CH
 * @title BaoMa
 * @date 2024/12/4 15:49
 * @description TODO
 */
public class BaoMa implements Car{
    @Override
    public void start() {
        System.out.println("---------------------- 启动宝马车辆 ----------------------");
    }

    @Override
    public void drive() {
        System.out.println("---------------------- 驾驶宝马车辆 ----------------------");
    }

    @Override
    public void end() {
        System.out.println("---------------------- 停止宝马车辆 ----------------------");
    }
}
