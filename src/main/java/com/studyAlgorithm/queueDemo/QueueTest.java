package com.studyAlgorithm.queueDemo;

/**
 * 使用链表来实现 队列 数据结构
 */
public class QueueTest<T> {

    private Node head;
    private Node last;
    private int len;

    public QueueTest(){
        this.head = new Node(null,null);
        this.last = null;
        this.len = 0;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public int size(){
        return len;
    }

    //向队列插入元素
    public void push(T t){
        if(len==0){
            //创建新节点
            Node newNode = new Node(t, head.next);
            head.next = newNode;
            last = newNode;
            len++;
        }else {
            //创建新节点
            Node newNode = new Node(t, head.next);
            head.next = newNode;
            len++;
        }
    }

    public T pop() {
        Node pre = head;
        for (int i = 0; i < len - 1; i++) {
            pre = pre.next;
        }
        Node returnData = pre.next;
        last = pre;
        pre.next = null;
        len--;
        return returnData.data;
    }

    private class Node{
        private Node next;
        private T data;
        public Node(T t, Node next){
            this.data = t;
            this.next = next;
        }
    }
}
