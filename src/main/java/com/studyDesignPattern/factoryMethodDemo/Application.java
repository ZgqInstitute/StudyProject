package com.studyDesignPattern.factoryMethodDemo;

abstract class Application {
	abstract Product createProduct();

	Product getObject() {
		Product product = createProduct();
		return product;
	}
}
