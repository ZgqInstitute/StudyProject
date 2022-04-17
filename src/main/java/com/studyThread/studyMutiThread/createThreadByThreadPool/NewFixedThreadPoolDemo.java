package com.studyThread.studyMutiThread.createThreadByThreadPool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPoolDemo {

    @Test
    public void newFixedThreadPoolTest() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程来处理任务");
            });
        }

    }


}
