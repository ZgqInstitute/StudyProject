package com.studyAlgorithm.ListDemo;


import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        TwoWayLinkedList<Integer> linkedList = new TwoWayLinkedList<>();
        linkedList.insert(1);
        linkedList.insert(3);
        linkedList.insert(5);
        linkedList.insert(7);
        linkedList.insert(9);
        linkedList.insert(11);

        for(int i = 0; i < linkedList.length(); i++){
            System.out.println(linkedList.get(i));
        }

        System.out.println();

        linkedList.del(2);
        for(int i = 0; i < linkedList.length(); i++){
            System.out.println(linkedList.get(i));
        }
    }
}
