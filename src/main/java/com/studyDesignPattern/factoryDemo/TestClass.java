package com.studyDesignPattern.factoryDemo;

import com.studyDesignPattern.factoryDemo.factoryMethod.Application;
import com.studyDesignPattern.factoryDemo.factoryMethod.CreateProduct01;
import com.studyDesignPattern.factoryDemo.factoryMethod.CreateProduct02;
import com.studyDesignPattern.factoryDemo.factoryMethod.Product;
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
