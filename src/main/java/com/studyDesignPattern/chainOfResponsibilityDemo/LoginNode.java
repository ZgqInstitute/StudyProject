package com.studyDesignPattern.chainOfResponsibilityDemo;

/**
 * 判断是否登录节点
 */
public class LoginNode extends Node{

    //当前节点处理完交给下一个节点进行处理
    public LoginNode(Node nextNode) {
        super(nextNode);
    }

    @Override
    boolean process(UserRequest userRequest) {
        System.out.println("登录控制");
        if(userRequest.isLogin()){

        }
        return false;
    }
}
