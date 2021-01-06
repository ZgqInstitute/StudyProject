package com.studyDesignPattern.singleDemo.lazy.useSynchronizedCodeThreadNoSafe;

public class SingleDemo {

	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		if (singleDemo == null) {
			// -->t1   -->t2
			synchronized (SingleDemo.class){
				//线程在获得锁后，直接创建对象会有线程安全问题
				singleDemo = new SingleDemo();
			}
		}
		return singleDemo;
	}
}
