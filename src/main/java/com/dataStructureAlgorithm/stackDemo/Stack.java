package com.dataStructureAlgorithm.stackDemo;

/**
 * 使用链表来模拟 栈 的数据结构
 */
public class Stack<T> {

    private Node head;//栈顶元素
    private int len;//栈内的元素个数

    public Stack(){
        this.head = new Node(null,null);
        this.len = 0;
    }

    //判断栈是否为空
    public boolean isEmpty(){
        return len ==0;
    }

    //获取栈内元素个数
    public int size(){
        return len;
    }

    //将t元素压栈
    public void push(T t){
        Node node = new Node(t, head.next);
        head.next = node;
        len++;
    }

    //弹出栈顶元素
    public T pop(){
        Node returnNode = head.next;
        head.next = head.next.next;
        len--;
        return returnNode.data;
    }

    private class Node{
        private T data;
        private Node next;
        public Node(T data, Node next){
            this.data = data;
            this.next = next;
        }
    }
}
