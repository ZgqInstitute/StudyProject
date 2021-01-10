package com.studyDesignPattern.factoryDemo.factoryMethod;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		Application01 application01  = new CreateProduct01();
		Product product = application01.getObject();
		product.method();

	}



}
