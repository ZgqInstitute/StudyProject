package com.studyDesignPattern.factoryDemo.simpleFactory;

/**
 * 胡椒披萨
 * */
public class Pepper extends Pizza {
	@Override
	public void prepare() {
		System.out.println("给胡椒披萨准备原材料");
	}
}
