package com.studyDesignPattern.factoryMethodDemo;

import org.junit.Test;

public class TestClass {
	@Test
	public void testMethod() {
		Application application = new CreateProduct01();
		Product product = application.getObject();
		product.method();

		Application application2 = new CreateProduct02();
		Product product2 = application2.getObject();
		product2.method();
	}
}
