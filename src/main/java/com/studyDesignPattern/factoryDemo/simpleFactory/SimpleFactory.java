package com.studyDesignPattern.factoryDemo.simpleFactory;

/**
 * 简单工厂：作用就是生产披萨（创建对象）
 */
public class SimpleFactory {

	/**
	 * 根据披萨类型，创建对应的披萨并返回
	 */
	public Pizza createPizza(String pizzaType) {
		Pizza pizza = null;
		if (pizzaType.equals("cheese")) {
			pizza = new CheesePizza();
			pizza.setName("奶酪披萨");
		} else if (pizzaType.equals("pepper")) {
			pizza = new Pepper();
			pizza.setName("胡椒披萨");
		}
		return pizza;
	}
}
