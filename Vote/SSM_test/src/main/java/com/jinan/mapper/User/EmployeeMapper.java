package com.jinan.mapper.User;

import com.jinan.entities.User.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 * Created by CH on 2024/2/12
 */
@Mapper
public interface EmployeeMapper {
    List<Employee> getEmployees();

    Employee findUserByName(String name);

    Employee findUserById(Integer id);

    void insertEmployee(Employee e);

    void delEmployee(Integer i);

    int updateEmployee(Employee e);
}
