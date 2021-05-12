package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

import java.util.Arrays;

/**
 * 反转单向链表，采用递归方法进行反转
 */
public class ReverseList {

    private Node head;
    private int len;

    public ReverseList() {
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

    /**
     * 反转整个链表
     * 反转链表方法一：采用递归的方式进行反转
     */
    public void reverseList(ReverseList reverseList) {
        //传入头结点
        reverseNodeWay01(reverseList.head.next);
    }

    //反转单个节点
    public Node reverseNodeWay01(Node node) {
        if (node.next == null) {
            head.next = node;
            return node;
        }
        Node pre = reverseNodeWay01(node.next);
        pre.next = node; //这一步关键
        node.next = null; //这一步关键
        return node;
    }

    /**
     * 反转链表方法二：不采用递归的方式进行反转
     */
    public void reverseNodeWay02() {
        Node curr = head.next;
        Node next = null;

        //定义新链表的头结点
        Node newHead = new Node(null, null);
        while (curr != null) {
            //先将当前节点的下一个节点保存到next中
            next = curr.next;
            //太妙了！！
            curr.next = newHead.next;
            //新链表的头结点始终指向新遍历到的节点
            newHead.next = curr;
            //将当前节点后移
            curr = next;
        }
        head = newHead;
    }

    /**
     * 从尾到头打印链表
     */
    public int[] reversePrint(){
        int[] arr = new int[len];
        Node node = head.next;
        for(int i = 1; i <= len; i++){
            arr[len - i] = node.data.intValue();
            node = node.next;
        }
        System.out.println(Arrays.toString(arr));
        return arr;
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
        ReverseList reverseList = new ReverseList();
        reverseList.insert(1);
        reverseList.insert(2);
        reverseList.insert(3);
        reverseList.insert(4);
        for (int i = 0; i < reverseList.length(); i++) {
            System.out.println(reverseList.get(i));
        }
        System.out.println();
        reverseList.reverseNodeWay02();
        for (int i = 0; i < reverseList.length(); i++) {
            System.out.println(reverseList.get(i));
        }
    }
}


