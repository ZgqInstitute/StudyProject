package com.studyFrame.springbootDemo.domain.mapper;

import com.studyFrame.springbootDemo.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringTransactionMapper {

    /**
     * 根据用户id查询用户
     *
     * @param UserId 用户id
     * @return 用户信息
     */
    User getUserById(Long UserId);

    /**
     * 转入方法
     *
     * @param inUserId 转入用户id
     * @param account  转入的钱
     * @return 影响行数
     */
    int transferAccounts_add(@Param("inUserId") int inUserId, @Param("account") int account);

    /**
     * 转出方法
     *
     * @param outUserId 转出用户id
     * @param account   转出的钱
     * @return 影响行数
     */
    int transferAccounts_sub(@Param("outUserId") int outUserId, @Param("account") int account);
}
