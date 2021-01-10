package com.studyDesignPattern.proxyDemo.dynamicProxy.jdkProxy;

/**
 * 目标对象
 * */
public class ManWaiter implements Waiter {
	@Override
	public void service() {
		System.out.println("目标对象执行：服务中。。。。。");
	}
}
