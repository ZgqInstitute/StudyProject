package com.studyFrame.springbootDemo.service;


public interface SpringTransactionService {

    /**
     * 转账方法
     * @param inUserId 转入用户id
     * @param outUserId 转出用户id
     * @param account 转账金额
     * @return 是否转账成功
     */
    boolean transfer(int inUserId, int outUserId, int account);
}
