package com.studyDesignPattern.decoratorDemo;

/**
 * 注：（1）抽象装饰类也要实现被增强接口
 *     （2）抽象类实现接口不用实现接口中的抽象方法
 * */
abstract class Decorator implements Original {
	Original original;

	/**
	 * 抽象类的构造方法：
	 *  因为抽象类的构造方法带了参数，所以继承了该抽象类的对象要在构造方法中显示调用super(original)
	 * */
	public Decorator(Original original) {
		this.original = original;
	}

}
