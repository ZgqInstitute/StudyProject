package com.studyDesignPattern.factoryMethodDemo;

public class CreateProduct01 extends Application {
	@Override
	Product createProduct() {
		return new Product01();
	}
}
