package com.studyDesignPattern.singleDemo.hungry.SingleStaticCode;

public class SingleDemo {

	//这里不能加final修饰，因为一旦加了final修饰singleDemo变量将不能改变
	private static SingleDemo singleDemo;

	static {
		singleDemo = new SingleDemo();
	}

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		return singleDemo;
	}

}
