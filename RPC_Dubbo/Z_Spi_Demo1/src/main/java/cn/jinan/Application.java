package cn.jinan;

import cn.jinan.Car.Car;
import java.util.ServiceLoader;

/**
 * @author CH
 * @title cn.jinan.Application
 * @date 2024/12/4 15:53
 * @description TODO
 */
public class Application {
    public static void main(String[] args) {
        ServiceLoader<Car> serviceLoader = ServiceLoader.load(Car.class);
        for (Car service : serviceLoader){
            System.out.println("service = " + service);
            Travel travel = new Travel(service);
            travel.go();
        }
    }
}
