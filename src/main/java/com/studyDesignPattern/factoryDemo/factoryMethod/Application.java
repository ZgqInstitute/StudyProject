package com.studyDesignPattern.factoryDemo.factoryMethod;


abstract class Application {
	abstract Product createProduct();

	Product getObject() {
		Product product = createProduct();
		return product;
	}
}
