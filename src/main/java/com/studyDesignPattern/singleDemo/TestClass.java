package com.studyDesignPattern.singleDemo;


import com.studyDesignPattern.singleDemo.lazy.doubleIfNoUseVolatile.SingleDemo;
import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod() {

		System.out.println("ssss");
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				SingleDemo instance1 = SingleDemo.getInstance();
				System.out.println(Thread.currentThread().getName()+"   "+instance1);
			}
		});
		thread1.start();


		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				SingleDemo instance2 = SingleDemo.getInstance();
				System.out.println(Thread.currentThread().getName()+"       "+instance2);
			}
		});
		thread2.start();

		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTT");

	}
}
