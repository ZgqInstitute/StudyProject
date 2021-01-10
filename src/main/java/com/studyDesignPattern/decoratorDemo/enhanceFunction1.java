package com.studyDesignPattern.decoratorDemo;

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
