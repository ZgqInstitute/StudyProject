package com.studyDesignPattern.chainOfResponsibilityDemo;


import org.junit.Test;

public class TestClass {
    @Test
    public void testMethod() {
        //创建用户请求对象
        UserRequest userRequest = new UserRequest.RequestBuild().isLoginMethod(true).isAuthorityMethod(true).isBusyMethod(false).build();
        //创建登录节点
        LoginNode loginNode = new LoginNode(new AuthorityNode(new BusyNode(null)));
        if (loginNode.process(userRequest)) {
            System.out.println("每个节点都执行成功");
        } else {
            System.out.println("有节点执行失败");
        }
    }
}
