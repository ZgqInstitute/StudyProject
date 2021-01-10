package com.studyDesignPattern.factoryDemo.simpleFactory;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		SimpleFactory simpleFactory = new SimpleFactory();
		Pizza pizza = simpleFactory.createPizza("pepper");
		if(pizza!=null){
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();
		}
	}
}
