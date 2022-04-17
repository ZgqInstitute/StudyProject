package com.studyThread.studyMutiThread.createThreadByCallable;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class CallableDemo {

    @Test
    public void CallableTest() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(() -> {
            System.out.println("线程名字：" + Thread.currentThread().getName());
            System.out.println("执行Callable的call方法喽");
            return 100;
        });

        new Thread(futureTask, "a线程").start();

        Integer re = futureTask.get();
        System.out.println("结果：" + re);
    }

}
