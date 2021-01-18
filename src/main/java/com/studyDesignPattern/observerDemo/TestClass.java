package com.studyDesignPattern.observerDemo;

import org.junit.Test;

public class TestClass {
	@Test
	public void testMethod(){
		Subject subject = new Subject();
		//给主题对象添加观察者对象
		Observer01 observer = new Observer01();
		subject.addObserver(observer);
		Observer02 observer1 = new Observer02();
		subject.addObserver(observer1);
		subject.notifyObserver("主题对象发生变化");

		//从主题对象中移除观察者对象
//		subject.removeObserver(observer);
	}
}
