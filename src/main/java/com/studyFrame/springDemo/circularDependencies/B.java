package com.studyFrame.springDemo.circularDependencies;


import org.springframework.beans.factory.annotation.Autowired;

public class B {
	@Autowired
	private A a;

	public B(){
		System.out.println("B的构造函数执行");
	}


	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}
