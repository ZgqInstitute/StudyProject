package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 反转单向链表，采用递归方法进行反转
 */
public class ReverseListWay01 {

    private Node head;
    private int len;

    public ReverseListWay01() {
        this.head = new Node(null, null);
        this.len = 0;
    }

    //获取链表的长度
    public int length() {
        return len;
    }

    //获取指定位置i处的元素
    public Integer get(int i) {
        Node curr = head.next;
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return curr.data;
    }

    //向链表的尾部添加节点
    public void insert(Integer t) {
        if (len == 0) {
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

    //反转整个链表
    public void reverseList(ReverseListWay01 reverseListWay01) {
        //传入头结点
        reverseNode(reverseListWay01.head.next);
    }

    //反转单个节点
    public Node reverseNode(Node node) {
        if (node.next == null) {
            head.next = node;
            return node;
        }
        Node pre = reverseNode(node.next);
        pre.next = node; //这一步关键
        node.next = null; //这一步关键
        return node;
    }

    private class Node {
        private Integer data;
        private Node next;

        public Node(Integer data, Node node) {
            this.data = data;
            this.next = node;
        }
    }

    @Test
    public void test() {
        ReverseListWay01 reverseListWay01 = new ReverseListWay01();
        reverseListWay01.insert(1);
        reverseListWay01.insert(2);
        reverseListWay01.insert(3);
        reverseListWay01.insert(4);
        for (int i = 0; i < reverseListWay01.length(); i++) {
            System.out.println(reverseListWay01.get(i));
        }

        System.out.println();

        reverseListWay01.reverseList(reverseListWay01);
        for (int i = 0; i < reverseListWay01.length(); i++) {
            System.out.println(reverseListWay01.get(i));
        }


    }
}


