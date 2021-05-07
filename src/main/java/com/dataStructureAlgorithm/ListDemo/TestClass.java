package com.dataStructureAlgorithm.ListDemo;


import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        LinkedListDemo<Integer> linkedList = new LinkedListDemo<>();
        linkedList.insert(1);
        linkedList.insert(3);
        linkedList.insert(5);
        linkedList.insert(7);

        for(int i = 0; i < linkedList.length(); i++){
            System.out.println(linkedList.get(i));
        }

    }
}
