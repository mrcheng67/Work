package cn.jinan;


import cn.jinan.Car.Car;

/**
 * @author CH
 * @title Travel
 * @date 2024/12/4 15:55
 * @description TODO
 */
public class Travel {
    private Car car;

    public Travel(Car car){
        this.car = car;
    }

    public void go(){
        car.start();
        car.drive();
        car.end();
    }
}
