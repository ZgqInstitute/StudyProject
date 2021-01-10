package com.studyDesignPattern.factoryDemo.factoryMethod;



public class CreateProduct02 extends SimpleFactory {
	@Override
	Product createProduct() {
		return new Product02();
	}
}
