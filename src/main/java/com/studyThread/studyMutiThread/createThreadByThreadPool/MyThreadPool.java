package com.studyThread.studyMutiThread.createThreadByThreadPool;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    @Test
    public void myThreadPoolDemo(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 6; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程来处理任务");
            });
        }

    }
}
