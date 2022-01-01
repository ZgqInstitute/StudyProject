package com.studyAlgorithm.ListDemo;

/**
 * 构建链表
 */
public class LinkedListDemo<T> {

    private Node head;
    private int len;

    public LinkedListDemo() {
        this.head = new Node(null, null);
        this.len = 0;
    }

    //清空链表方法
    public void clear() {
        head.next = null;
        len = 0;
    }

    //获取链表的长度
    public int length() {
        return len;
    }

    //判断链表是否为空
    public boolean isEmpty() {
        return len == 0;
    }

    //获取指定位置i处的元素
    public T get(int i) {
        Node curr = head.next;
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return curr.data;
    }

    //向链表的尾部添加节点
    public void insert(T t) {
        if(len == 0){
            head.next = new Node(t, null);
            len++;
            return;
        }
        Node last = head.next;
        while (last.next != null) {
            last = last.next;
        }
        Node node = new Node(t, null);
        last.next = node;
        len++;
    }

    //向链表的指定位置i添加元素
    public void insert(int i, T t) {
        Node pre = head.next;
        for (int j = 0; j < i - 1; j++) {
            pre = pre.next;
        }
        Node nextNode = pre.next;
        Node node = new Node(t, nextNode);
        pre.next = node;
        len++;
    }

    //删除链表中指定位置i处的元素
    public T del(int i) {
        Node pre = head.next;
        for (int j = 0; j < i - 1; j++) {
            pre = pre.next;
        }
        Node curr = pre.next;
        Node nextNode = curr.next;
        pre.next = nextNode;
        len--;
        return curr.data;
    }

    //查找元素t第一次在链表中出现的位置
    public int indexOf(T t) {
        Node node = head.next;
        for (int j = 0; j < len; j++) {
            if (node.data.equals(t)) {
                return j;
            }
            node = node.next;
        }
        return -1;
    }

    //创建一个内部类，封装节点信息
    private class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}

