package com.studyFrame.springDemo.circularDependencies;


import org.springframework.beans.factory.annotation.Autowired;

public class A {
	@Autowired
	private B b;

	public A(){
		System.out.println("A的构造函数执行");
	}

	public void testAOP(){
		System.out.println("................");
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
}
