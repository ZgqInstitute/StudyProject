package com.studyDesignPattern.decoratorDemo;

/**
 * 被增强方法的封装类
 * */
public class OriginalClass implements Original {
	@Override
	public void originalMethod() {
		System.out.println("单品咖啡");
	}
}

