package com.jinan.service.impl;

import com.jinan.entities.Employee;
import com.jinan.mapper.EmployeeMapper;
import com.jinan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:
 * Created by GH on 2021/2/12
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

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
        employeeMapper.updateEmployee(e);
    }

}
