package com.jinan.mapper;

import com.jinan.entities.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 * Created by GH on 2021/2/12
 */
@Mapper
@Repository
public interface EmployeeMapper {
    List<Employee> getEmployees();

    Employee findUserById(Integer id);

    void insertEmployee(Employee e);

    void delEmployee(Integer i);

    int updateEmployee(Employee e);
}
