package com.studyThread.studyMutiThread.createThreadByThread;

import org.junit.Test;

public class ThreadDemo {

    @Test
    public void threadTest() {
        Person p1 = new Person("张三");
        Person p2 = new Person("李四");
        p1.run();
        p2.run();
        System.out.println("---------------");
        p1.start();
        p2.start();
    }
}
