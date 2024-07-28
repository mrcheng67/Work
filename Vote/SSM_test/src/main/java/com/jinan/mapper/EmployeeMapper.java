package com.jinan.mapper;

import com.jinan.entities.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 * Created by GH on 2021/2/12
 */
@Mapper
public interface EmployeeMapper {
    List<Employee> getEmployees();

   void insertEmployee(Employee e);

   void delEmployee(Integer i);

   void updateEmployee(Employee e);
}
