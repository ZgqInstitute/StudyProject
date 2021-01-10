package com.studyDesignPattern.factoryDemo.factoryMethod;


public class CreateProduct01 extends SimpleFactory {
	@Override
	Product createProduct() {
		return new Product01();
	}
}
