package com.jinan.controller.User;

import com.jinan.Annotation.LogAnnotation;
import com.jinan.entities.User.Employee;
import com.jinan.service.User.EmployeeService;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by GH on 2021/2/12
 */
//@RestController
@Controller
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/user/getOne")
    @LogAnnotation("查找特定人物")
    public String getOne(Integer id, Map<String,Object> map){
        List<Employee> employees1 = new ArrayList<>();
        Employee employees = employeeService.findUserById(id);
        employees1.add(employees);      // 加到list中 ， 保证数据是可以迭代的类型
        map.put("emps",employees1);
        return "/user/list";
    }

    @LogAnnotation("查找所有人物")
    @RequestMapping(value = "/user/getEmployee")
    public String getEmployee(Map<String,Object> map){
        //调用EmployeeService中的获取员工的方法
        List<Employee> employees = employeeService.getEmployee();
        map.put("emps",employees);
        return "/user/list";
    }
    //跳转到添加页面
    @LogAnnotation("跳转到添加人物网页")
    @RequestMapping("/user/toAdd")
    public String toAddNumber(){
        return "/user/add";
    }

    //添加请求
    @LogAnnotation("添加人物")
    @RequestMapping("/user/add")
    public String addNumber(Employee e){
        employeeService.insertEmployee(e);
        return "redirect:/user/getEmployee"; //重定向到@RequestMapping("/allBook")请求
    }
    //删除请求
    //@LogAnnotation("删除人物")
    @RequestMapping("/user/del")
    public String DelNumber(@RequestParam("id")Integer i){
        System.out.println("删除成员号 = "+ i);
        employeeService.delEmployee(i);
        return "redirect:/user/getEmployee"; //重定向到@RequestMapping("/allBook")请求
    }

    @RequestMapping("/user/toup")
    public String toupdate(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "user/update"; // 返回编辑页面的视图名称
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String UpdateNum(Employee e) {
        employeeService.updateEmployee(e);
        return "redirect:/user/getEmployee";
    }
}
