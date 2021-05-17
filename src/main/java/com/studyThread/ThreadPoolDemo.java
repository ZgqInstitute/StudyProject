package com.studyThread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用线程池
 */
public class ThreadPoolDemo {

	@Test
	public void test() {

		ReentrantLock reentrantLock = new ReentrantLock();

		//使用newFixedThreadPool线程池
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

        //使用newSingleThreadExecutor线程池
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        //使用newCachedThreadPool线程池
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		try {
			for (int i = 0; i < 10; i++) {
				cachedThreadPool.execute(() -> {
							System.out.println(Thread.currentThread().getName() + "线程处理任务");
						}
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭线程
			cachedThreadPool.shutdown();
		}
	}
}
