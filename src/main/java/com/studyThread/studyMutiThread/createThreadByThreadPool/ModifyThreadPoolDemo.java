package com.studyThread.studyMutiThread.createThreadByThreadPool;

import java.util.concurrent.*;

/**
 * 动态修改线程池的参数
 *
 */
public class ModifyThreadPoolDemo {
    public static void main(String[] args) {
        testModifyThreadPool();
    }

    private static void testModifyThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ThreadPoolExecutor executor = buildThreadPoolExecutor();

        executor.setCorePoolSize(10);
    }





    /**
     * 构建线程池
     */
    private static ThreadPoolExecutor buildThreadPoolExecutor(){
        return new ThreadPoolExecutor(
                2,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
