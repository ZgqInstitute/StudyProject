package com.studyDesignPattern.factoryDemo.factoryMethod;



public class CreateProduct02 extends Application01 {
	@Override
	Product createProduct() {
		return new Product02();
	}
}
