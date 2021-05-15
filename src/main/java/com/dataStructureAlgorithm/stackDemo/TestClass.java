package com.dataStructureAlgorithm.stackDemo;

import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        Stack<Integer> integerStack = new Stack<>();
        integerStack.push(1);
        integerStack.push(2);
        integerStack.push(3);

        System.out.println(integerStack.pop() + "----" + integerStack.size());
        System.out.println(integerStack.pop() + "----" + integerStack.size());
        System.out.println(integerStack.pop() + "----" + integerStack.size());

    }
}
