package com.studyAlgorithm.stackDemo;

import org.junit.Test;

public class TestClass {

    @Test
    public void test(){
        ZGQStack<Integer> integerZGQStack = new ZGQStack<>();
        integerZGQStack.push(1);
        integerZGQStack.push(2);
        integerZGQStack.push(3);

        System.out.println(integerZGQStack.pop() + "----" + integerZGQStack.size());
        System.out.println(integerZGQStack.pop() + "----" + integerZGQStack.size());
        System.out.println(integerZGQStack.pop() + "----" + integerZGQStack.size());

    }
}
