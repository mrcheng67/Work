package com.jinan.service.User;

import com.jinan.entities.User.Employee;

import java.util.List;

/**
 * description:
 * Created by GH on 2021/2/12
 */
public interface EmployeeService {

    List<Employee> getEmployee();

    Employee findUserByName(String name);

    Employee findUserById(Integer id);

    void insertEmployee(Employee e);

    void delEmployee(Integer i);

    void updateEmployee(Employee e);
}
