package com.studyFrame.springDemo.AOP;


import org.springframework.beans.factory.annotation.Autowired;

public class TargetClass {

	@Autowired
	public A a;

	public int targetMethod(int i,int j){
		System.out.println("目标方法执行");
		return i/j;
	}

}
