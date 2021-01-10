package com.studyDesignPattern.factoryDemo.simpleFactory;

/**
 * 奶酪披萨
 * */
public class CheesePizza extends Pizza {
	@Override
	public void prepare() {
		System.out.println("给奶酪披萨准备原材料");
	}
}
