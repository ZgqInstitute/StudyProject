package com.studyDesignPattern.decoratorDemo;

/**
 * 增强方法的封装类2
 * */
public class EnhanceFunction02 extends Decorator {

	public EnhanceFunction02(Original original){
		super(original);
	}

	@Override
	public void originalMethod() {
		System.out.println("加牛奶");
		original.originalMethod();
	}
}
