package com.studyFrame.springDemo.AOP;

public class TargetClass {

	public int targetMethod(int i,int j){
		System.out.println("目标方法执行");
		return i/j;
	}

}
