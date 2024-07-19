package com.studyFrame.springbootDemo.controller;

import com.studyFrame.springbootDemo.config.ZGQAnnotation;
import com.studyFrame.springbootDemo.domain.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试自定义注解
 * 实现日志打印：方法的出入参、接口耗时等一些需要关注的消息都可以打印日志
 */
@RestController
@RequestMapping("/hello")
public class CustomAnnotationController {

    @ZGQAnnotation
    @GetMapping("/get")
    public User get(@RequestParam("userId") Integer userId){
        User user = new User();
        user.setId(100L);
        user.setAge(18);
        user.setSex(1);
        user.setName("王阳明");
        user.setSchool("浙江大学");
        return user;
    }

    @ZGQAnnotation
    @GetMapping("/update")
    public String update(@RequestParam("perId") String perId, @RequestParam("age") Integer age){
        return "hello update";
    }
}
