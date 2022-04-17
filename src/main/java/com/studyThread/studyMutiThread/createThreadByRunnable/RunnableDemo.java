package com.studyThread.studyMutiThread.createThreadByRunnable;

import org.junit.Test;

public class RunnableDemo {

    @Test
    public void runnableTest(){
        Person p = new Person("paul");

        Thread thread1 = new Thread(p);
        Thread thread2 = new Thread(p);
        thread1.start();
        thread2.start();

    }
}
