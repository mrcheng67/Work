package com.jinan.service.User.impl;

import com.jinan.entities.User.Employee;
import com.jinan.mapper.User.EmployeeMapper;

import com.jinan.service.User.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description:
 * Created by GH on 2021/2/12
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Employee findUserByName(String name) {

        return employeeMapper.findUserByName(name);
    }
    /**
     * 获取用户策略:先从缓存中获取用户，没有则读mysql数据，再将数据写入缓存
     */
    @Override
    public Employee findUserById(Integer id){
        String key = "user_"+id;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if(hasKey){
            Employee user = (Employee) operations.get(key);
            System.out.println("从缓存中获取数据:"+user.toString());
            System.out.println("-----------------------------");
            return user;
        }else{
            Employee user = employeeMapper.findUserById(id);
            System.out.println("查询数据库获取数据:" + user.toString());
            System.out.println("------------写入缓存---------------------");
            //写入缓存
            operations.set(key,user,5, TimeUnit.HOURS);
            return user;
        }
    }

    @Override
    public List<Employee> getEmployee() {
        //调用employeeMapper中获取所有员工的方法
        return employeeMapper.getEmployees();
    }

    @Override
    public void insertEmployee(Employee e) {
        employeeMapper.insertEmployee(e);
    }

    @Override
    public void delEmployee(Integer i) {
        employeeMapper.delEmployee(i);
    }

    @Override
    public void updateEmployee(Employee e) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        int result = employeeMapper.updateEmployee(e);
        if (result != 0) {
            String key = "user_" + e.getId();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key-----------> " + key);
            }
            // 再将更新后的数据加入缓存
            Employee userNew = employeeMapper.findUserById(e.getId());
            System.out.println("Redis中更新的数据为" + userNew);
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }
    }
}
