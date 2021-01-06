package com.studyDesignPattern.singleDemo.lazy.useSynchronizedThreadSafe;

public class SingleDemo {
	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public synchronized static SingleDemo getInstance() {
		if (singleDemo == null) {
			try {Thread.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
			singleDemo = new SingleDemo();
		}
		return singleDemo;
	}
}
