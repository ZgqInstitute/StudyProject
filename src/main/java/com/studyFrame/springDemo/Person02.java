package com.studyFrame.springDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class Person02 implements BeanPostProcessor {

	private String name;

	Person02(){}

	Person02(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Person02 --> Before");
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Person02 --> After");
		return null;
	}


	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				'}';
	}
}
