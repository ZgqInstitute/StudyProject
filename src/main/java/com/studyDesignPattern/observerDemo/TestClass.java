package com.studyDesignPattern.observerDemo;

import org.junit.Test;

public class TestClass {
	@Test
	public void testMethod(){
		Subject subject = new Subject();
		subject.addObserver(new Observer01());
		subject.addObserver(new Observer02());
		subject.notifyObserver("主题对象发生变化");
	}
}
