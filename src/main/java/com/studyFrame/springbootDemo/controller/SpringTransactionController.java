package com.studyFrame.springbootDemo.controller;

import com.studyFrame.springbootDemo.service.SpringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个接口的作用是测试spring的事务
 */
@RestController
@RequestMapping("/transfer")
public class SpringTransactionController {
    @Autowired
    private SpringTransactionService springTransactionService;

    @GetMapping("/{inUserId}/{outUserId}/{transferMoney}")
    public String getUserById(@PathVariable Integer inUserId, @PathVariable Integer outUserId, @PathVariable Integer transferMoney) {
        try {
            springTransactionService.transfer(inUserId, outUserId, transferMoney);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "转账成功";
    }


}
