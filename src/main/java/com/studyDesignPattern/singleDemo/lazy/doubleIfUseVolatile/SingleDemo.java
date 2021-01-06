package com.studyDesignPattern.singleDemo.lazy.doubleIfUseVolatile;

public class SingleDemo {

	private static volatile SingleDemo singleDemo;

	private SingleDemo() {
	}

	public synchronized static SingleDemo getInstance() {
		if (singleDemo == null) {
			synchronized (SingleDemo.class) {
				if (singleDemo == null) {
					singleDemo = new SingleDemo();
				}
			}
		}
		return singleDemo;
	}
}
