package com.studyFrame.springDemo.AOP;

import org.springframework.beans.factory.annotation.Autowired;

public class A {

	@Autowired
	private TargetClass targetClass;

	public TargetClass getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(TargetClass targetClass) {
		this.targetClass = targetClass;
	}
}
