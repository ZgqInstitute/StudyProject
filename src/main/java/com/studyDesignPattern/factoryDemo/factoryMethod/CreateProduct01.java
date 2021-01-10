package com.studyDesignPattern.factoryDemo.factoryMethod;


public class CreateProduct01 extends Application01 {
	@Override
	Product createProduct() {
		return new Product01();
	}
}
