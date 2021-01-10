package com.studyDesignPattern.factoryDemo.factoryMethod;

/**
 *对于简单工厂是在工厂类中直接创建对象并返回；
 *对于工厂方法，只定义创建对象的抽象方法，具体创建对象的操作由子类来完成，工厂方法模式是将创建对象推迟到子类
 * */
public abstract class SimpleFactory {
	/**
	 * 创建对象的抽象方法，具体创建对象的操作由子类来完成
	 * */
	abstract Product createProduct();

	Product getObject() {
		Product product = createProduct();
		return product;
	}
}
