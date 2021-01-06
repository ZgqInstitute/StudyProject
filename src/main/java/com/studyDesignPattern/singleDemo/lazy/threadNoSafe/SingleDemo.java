package com.studyDesignPattern.singleDemo.lazy.threadNoSafe;

public class SingleDemo {
	//这里不能加final修饰，一旦加了final修饰就要赋初值，而且后面不能重新赋值
	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		if (singleDemo == null) {
			// -->t1   -->t2
			singleDemo = new SingleDemo();
		}
		return singleDemo;
	}
}
