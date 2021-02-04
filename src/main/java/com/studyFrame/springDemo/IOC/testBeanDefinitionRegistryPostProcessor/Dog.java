package com.studyFrame.springDemo.IOC.testBeanDefinitionRegistryPostProcessor;

public class Dog {

	private String name;

	public Dog(){
		System.out.println("Dog的构造函数执行");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Dog{" +
				"name='" + name + '\'' +
				'}';
	}
}
