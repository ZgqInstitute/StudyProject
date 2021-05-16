package com.dataStructureAlgorithm.queueDemo;

import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        QueueTest<String> queueTest = new QueueTest<>();
        queueTest.push("a");
        queueTest.push("b");
        queueTest.push("c");
        queueTest.push("d");

        System.out.println(queueTest.pop());
        System.out.println(queueTest.pop());
        System.out.println(queueTest.pop());
        System.out.println(queueTest.pop());
    }
}
