package com.studyDesignPattern.factoryDemo.factoryMethod;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		SimpleFactory simpleFactory = new CreateProduct01();
		Product product = simpleFactory.getObject();
		product.method();

	}

}
