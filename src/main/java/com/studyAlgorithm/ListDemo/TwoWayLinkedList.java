package com.studyAlgorithm.ListDemo;

/**
 * 构建双向链表
 */
public class TwoWayLinkedList<T> {

    private Node head;
    private Node last;
    private int len;

    public TwoWayLinkedList() {
        this.head = new Node(null, null, null);
        this.last = null;//刚开始尾结点为null
        this.len = 0;
    }

    //清空链表方法
    public void clear() {
        this.head.next = null;
        this.head.data = null;
        this.last = null;
        this.len = 0;
    }

    //获取链表的长度
    public int length() {
        return len;
    }

    //判断链表是否为空
    public boolean isEmpty() {
        return len == 0;
    }

    //获取双向链表的第一个元素
    public T getFirst() {
        if (len == 0) {
            return null;
        }
        Node next = head.next;
        return next.data;
    }

    //获取双向链表的最后一个元素
    public T getLast() {
        if (len == 0) {
            return null;
        }
        return last.data;
    }

    //向链表的尾部添加节点
    public void insert(T t) {
        //链表为空时
        if (isEmpty()) {
            last = new Node(head, t, null);
            head.next = last;
            len++;
            return;
        }

        //链表不为空时
        Node lastNode = new Node(last, t, null);
        last.next = lastNode;
        last = lastNode;
        len++;
    }

    //向链表的指定位置i添加元素
    public void insert(int i, T t) {
        Node preNode = head.next;
        for (int j = 0; j < i - 1; j++) {
            preNode = preNode.next;
        }
        Node node = new Node(preNode, t, preNode.next);
        preNode.next = node;
        preNode.next.pre = node;
        len++;
    }

    //获取指定位置i处的元素
    public T get(int i) {
        if (isEmpty()) {
            return null;
        }
        Node node = head.next;
        for (int j = 0; j < i; j++) {
            node = node.next;
        }
        return node.data;
    }

    //删除链表中指定位置i处的元素
    public T del(int i) {
        Node preNode = head.next;
        for (int j = 0; j < i - 1; j++) {
            preNode = preNode.next;
        }
        preNode.next = preNode.next.next;
        preNode.next.next.pre = preNode;
        len--;
        return preNode.next.data;
    }


    //定义一个Node内部类，封装节点数据
    private class Node {
        private Node pre;
        private Node next;
        private T data;

        public Node(Node pre, T data, Node next) {
            this.pre = pre;
            this.data = data;
            this.next = next;
        }
    }

}
