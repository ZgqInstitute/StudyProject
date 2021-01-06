package com.studyDesignPattern.singleDemo.hungry.SingleStaticConstant;

public class SingleDemo {
	/**
	 * private 加private的原因：
	 * static 加static的原因：
	 * final 加final的原因：
	 */
	private static final SingleDemo singleDemo = new SingleDemo();

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		return singleDemo;
	}

}
