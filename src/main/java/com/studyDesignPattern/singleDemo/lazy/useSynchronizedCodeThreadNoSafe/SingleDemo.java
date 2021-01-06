package com.studyDesignPattern.singleDemo.lazy.useSynchronizedCodeThreadNoSafe;

public class SingleDemo {

	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		if (singleDemo == null) {
			// -->t1   -->t2
			synchronized (SingleDemo.class){
				singleDemo = new SingleDemo();
			}
		}
		return singleDemo;
	}
}
