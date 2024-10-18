package com.jinan.controller;

import com.jinan.Annotation.LogAnnotation;
import com.jinan.entities.Employee;
import com.jinan.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by GH on 2021/2/12
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/getEmployee",method = RequestMethod.GET)
    @LogAnnotation("展示所有人")
    public String getEmployee(Map<String,Object> map){
        //调用EmployeeService中的获取员工的方法
        List<Employee> employees = employeeService.getEmployee();
        map.put("emps",employees);
        return "list";
    }
    //跳转到添加页面
    @LogAnnotation("跳转到添加人物网页")
    @RequestMapping("/toAdd")
    public String toAddNumber(){
        return "add";
    }

    //添加请求
    @LogAnnotation("添加人物")
    @RequestMapping("/add")
    public String addNumber(Employee e){
        employeeService.insertEmployee(e);
        return "redirect:/getEmployee"; //重定向到@RequestMapping("/allBook")请求
    }
    //删除请求
    @LogAnnotation("删除人物")
    @RequestMapping("/del")
    public String DelNumber(@RequestParam("empId")Integer i){
        employeeService.delEmployee(i);
        return "redirect:/getEmployee"; //重定向到@RequestMapping("/allBook")请求
    }

    //跳转到添加书籍页面
    @RequestMapping("/toup")
    public String toupdate(@RequestParam("empId")Integer i){
        return "update";
    }

    @RequestMapping("/update")
    public String UpdateNum(Employee e){
        employeeService.updateEmployee(e);
        return "redirect:/getEmployee";
    }
}
