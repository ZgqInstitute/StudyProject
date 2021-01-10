package com.studyDesignPattern.factoryDemo.factoryMethod;


public abstract class Application01 {
	abstract Product createProduct();

	Product getObject() {
		Product product = createProduct();
		return product;
	}
}
