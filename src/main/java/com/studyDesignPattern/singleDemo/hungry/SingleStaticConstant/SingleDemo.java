package com.studyDesignPattern.singleDemo.hungry.SingleStaticConstant;

public class SingleDemo {
	/**
	 * private 加private的原因：若不加private修饰，则可以直接通过SingleDemo.singleDemo来获得SingleDemo对象，getInstance()方法都可以删除，
	 *                          使用getInstance()方法来获得SingleDemo对象可以实现可控；
	 * static 加static的原因：因为getInstance()方法是静态的，静态方法只能调用静态变量；
	 * final 加final的原因：因为这里的引用变量singleDemo是固定不变的，所以加final修饰；
	 */
	private static final SingleDemo singleDemo = new SingleDemo();

	private SingleDemo() {
	}

	public static SingleDemo getInstance() {
		return singleDemo;
	}

}
