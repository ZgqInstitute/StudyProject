package com.studyDesignPattern.chainOfResponsibilityDemo;

/**
 * 判断是否授权节点
 */
public class AuthorityNode extends Node {

    //通过构造函数接收下一个节点，
    //当前节点处理完后交给下一个节点进行处理
    public AuthorityNode(Node nextNode) {
        super(nextNode);
    }

    @Override
    boolean process(UserRequest userRequest) {
        //判断是否登录成功
        if (userRequest.isAuthority()) {
            System.out.println("授权处理成功");
            //登录成功，获取下一个节点
            Node nextNode = getNextNode();
            //若下一个节点为null，说明当前节点是最后一个节点
            if (nextNode == null) {
                return true;
            }
            if (!nextNode.process(userRequest)) {
                //若下一个节点处理失败则返回false
                return false;
            } else {
                return true;
            }
        }
        //若当前节点处理失败，直接返回false
        System.out.println("授权失败");
        return false;
    }
}
