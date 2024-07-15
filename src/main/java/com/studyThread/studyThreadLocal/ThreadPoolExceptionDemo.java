package com.studyThread.studyThreadLocal;

import org.junit.Test;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExceptionDemo {

    @Test
    public void threadPoolExceptionDemo() {
        /**
         * 自定义线程池，重写afterExecute方法，来做子线程抛异常的兜底处理
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                new ThreadPoolExecutor.AbortPolicy()
        ) {
            @Override
            protected void afterExecute(Runnable runnable, Throwable throwable) {
                // 若程序员使用的是executor()，若子线程抛异常了，那么在该if进行处理
                if (throwable != null) {
                    System.out.println("afterExecute，executor()，异常了傻逼。。。。");
                }
                // 若程序员只使用的是submit()，没有调用get()，若子线程抛异常了，那么在该if进行处理
                if (throwable == null && runnable instanceof Future<?>) {
                    try {
                        Future<?> future = (Future<?>) runnable;
                        if (future.isDone()) {
                            // 若有程序员只调用了submit()，没有调用get()，那么通过这里来强制的调用get()
                            future.get();
                        }
                    } catch (Exception e) {
                        System.out.println("afterExecute，submit()，没有get()，异常了傻逼。。。。");
                    }
                }
            }
        };
    }

}
