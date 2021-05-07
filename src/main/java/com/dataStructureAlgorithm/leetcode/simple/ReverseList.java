package com.dataStructureAlgorithm.leetcode.simple;

import org.junit.Test;

/**
 * 需求：输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *  示例：
 *       输入：head = [1,3,2]
 *       输出：[2,3,1]
 */
public class ReverseList {

    @Test
    public void test(){

    }

    public Node reverseList(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;

    }

    private class Node {
        private Node next;
        private int data;
        private Node(Node head, int data){
            this.next = head;
            this.data = data;
        }
    }
}


