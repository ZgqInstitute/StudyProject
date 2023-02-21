package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.User;
import com.studyFrame.springbootDemo.domain.mapper.SpringTransactionMapper;
import com.studyFrame.springbootDemo.service.SpringTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SpringTransactionServiceImpl implements SpringTransactionService {

    @Resource
    private SpringTransactionMapper springTransactionMapper;

    /**
     * @Transactional可以放在哪些位置：1）service实现类的方法上；2）service实现类上；3）service接口的方法上；4）service接口上；
     * 四个位置按照上面1、2、3、4的顺序进行解析
     */
    @Transactional
    public boolean transfer(int inUserId, int outUserId, int account) {
        // 校验转出用户的金额是否足够
        User user = springTransactionMapper.getUserById((long) outUserId);
        if (user.getMoney() < account) {
            throw new RuntimeException("转出用户的金额不够");
        }
        try {
            springTransactionMapper.transferAccounts_sub(outUserId, account);
            // 人为制造异常
            if (account == 38) {
                throw new RuntimeException("转账失败");
            }
            springTransactionMapper.transferAccounts_add(inUserId, account);
        } catch (Exception e) {
            throw new RuntimeException("转账失败");
        }
        return true;
    }

}
