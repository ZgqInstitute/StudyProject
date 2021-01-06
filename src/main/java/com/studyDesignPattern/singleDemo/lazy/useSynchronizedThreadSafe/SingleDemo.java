package com.studyDesignPattern.singleDemo.lazy.useSynchronizedThreadSafe;

public class SingleDemo {
	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public synchronized static SingleDemo getInstance() {
		if (singleDemo == null) {//当SingleDemo对象不为null时，这一行代码没必要加锁
			singleDemo = new SingleDemo();
		}
		return singleDemo;
	}
}
