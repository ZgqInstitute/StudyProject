package com.studyDesignPattern.factoryDemo.factoryMethod;



public class CreateProduct02 extends Application {
	@Override
	Product createProduct() {
		return new Product02();
	}
}
