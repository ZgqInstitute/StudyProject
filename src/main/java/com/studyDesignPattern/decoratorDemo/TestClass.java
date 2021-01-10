package com.studyDesignPattern.decoratorDemo;

import org.junit.Test;

public class TestClass {
	@Test
	public void testMethod(){
		Original original = new OriginalClass();
		original.originalMethod();
		System.out.println("------------------");
		enhanceFunction1 enhanceFunction1 = new enhanceFunction1(original);
		enhanceFunction1.originalMethod();
		System.out.println("------------------");
		EnhanceFunction02 enhanceFunction02 = new EnhanceFunction02(enhanceFunction1);
		enhanceFunction02.originalMethod();
	}
}
