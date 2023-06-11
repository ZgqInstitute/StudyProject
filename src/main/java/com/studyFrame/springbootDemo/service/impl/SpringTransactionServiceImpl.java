package com.studyFrame.springbootDemo.service.impl;

import com.studyFrame.springbootDemo.domain.entity.User;
import com.studyFrame.springbootDemo.domain.mapper.SpringTransactionMapper;
import com.studyFrame.springbootDemo.service.SpringTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

@Slf4j
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

        // 获取事务名
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        System.out.println("事务name01 = " + currentTransactionName);

        // 校验转出用户的金额是否足够
        User user = springTransactionMapper.getUserById((long) outUserId);
        if (user.getMoney() < account) {
            throw new RuntimeException("转出用户的金额不够");
        }
        try {
            springTransactionMapper.transferAccounts_sub(outUserId, account);

            /**
             * 该方法可以控制在事务提交前执行、还是事务提交后执行
             * 说明：这个只能在事务中使用，要是没有事务，会抛异常
             *
             * todo 测试：
             *    因为afterCommit()方法是在事务提交后执行，那么在afterCommit()方法内若有数据库的DML操作，会如何执行？会新开启一个事务？
             *    参考：https://blog.csdn.net/qq330983778/article/details/112255441
             */
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        // 事务提交后执行afterCommit方法
                        @Override
                        public void afterCommit() {
                            System.out.println("afterCommit----事务执行结束了");

                            String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
                            System.out.println("事务name02 = " + currentTransactionName);

                            springTransactionMapper.transferAccounts_sub(3, 100);
                        }
                    });

            // 人为制造异常
            if (account == 38) {
                throw new RuntimeException("转账失败");
            }
            springTransactionMapper.transferAccounts_add(inUserId, account);
        } catch (Exception e) {
            throw new RuntimeException("转账失败，异常了傻逼");
        }
        return true;
    }

}
