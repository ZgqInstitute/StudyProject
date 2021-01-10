package com.studyDesignPattern.proxyDemo.dynamicProxy.jdkProxy;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		//创建代理工厂
		ProxyFactory proxyFactory = new ProxyFactory();
		//设置目标对象
		proxyFactory.setTargetObject(new ManWaiter());
		//设置前置增强
		proxyFactory.setBeforeAdvice(new BeforeAdvice() {
			@Override
			public void before() {
				System.out.println("前置增强执行：服务前《《《《");
			}
		});
		//设置后置增强
		proxyFactory.setAfterAdvice(new AfterAdvice() {
			@Override
			public void after() {
				System.out.println("后置增强执行：服务结束》》》》");
			}
		});

		//调用代理工厂的createProxy()方法来创建对象
		Waiter proxyInstance = (Waiter)proxyFactory.createProxy();
		proxyInstance.service();
	}
}
