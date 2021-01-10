package com.studyDesignPattern.factoryDemo.factoryMethod;


public class CreateProduct01 extends Application {
	@Override
	Product createProduct() {
		return new Product01();
	}
}
