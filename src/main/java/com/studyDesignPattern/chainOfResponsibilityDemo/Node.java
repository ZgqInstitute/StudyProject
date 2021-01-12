package com.studyDesignPattern.chainOfResponsibilityDemo;

/**
 * 用户请求需要经过的节点封装
 */
public abstract class Node {
    Node nextNode;

    public Node(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * 每个节点都会执行自己的任务，只有当前节点返回true才会继续将userRequest传给下一个节点
     */
    abstract boolean process(UserRequest userRequest);
}
