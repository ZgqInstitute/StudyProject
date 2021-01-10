package com.studyDesignPattern.decoratorDemo;

/**
 * 增强方法的封装类1
 * */
public class enhanceFunction1 extends Decorator {

	public enhanceFunction1(Original original) {
		super(original);
	}

	@Override
	public void originalMethod() {
		System.out.println("加糖");
		original.originalMethod();
	}
}
