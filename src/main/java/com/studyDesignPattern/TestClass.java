package com.studyDesignPattern;

import com.studyDesignPattern.singleDemo.hungry.SingleStaticConstant.SingleDemo;
import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		SingleDemo instance1 = SingleDemo.getInstance();
		SingleDemo instance2 = SingleDemo.getInstance();
		System.out.println(instance1 == instance2);

	}
}
