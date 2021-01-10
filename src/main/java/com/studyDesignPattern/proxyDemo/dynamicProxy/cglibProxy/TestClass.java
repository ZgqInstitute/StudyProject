package com.studyDesignPattern.proxyDemo.dynamicProxy.cglibProxy;

import org.junit.Test;

public class TestClass {
	@Test
	public void testMethod() {
		//创建目标对象
		TeacherDao target = new TeacherDao();
		//创建代理工厂
		ProxyFactory proxyFactory = new ProxyFactory(target);
		//调用代理工厂的创建代理对象方法
		TeacherDao proxyInstance = (TeacherDao) proxyFactory.getProxyInstance();

		//调用代理对象的teach()方法，实际上会调用intercept()方法
		proxyInstance.teach();
	}
}
