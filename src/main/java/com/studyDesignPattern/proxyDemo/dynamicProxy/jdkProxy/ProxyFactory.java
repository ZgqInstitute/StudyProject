package com.studyDesignPattern.proxyDemo.dynamicProxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂：代理工厂的作用是由调用代理工厂的调用者来传目标对象与增强内容，代理工厂的作用仅仅是装配作用
 */
public class ProxyFactory {
	private Object targetObject;//目标对象
	private BeforeAdvice beforeAdvice;//前置增强
	private AfterAdvice afterAdvice;//后置增强

	/**
	 * 该方法的作用：创建代理对象
	 */
	public Object createProxy() {
		//先准备好Proxy的newProxyInstance()方法的三个参数

		//参数1：类加载器（这个类加载器是加载目标对象的类加载器）
		ClassLoader classLoader = targetObject.getClass().getClassLoader();
		//参数2：目标对象所实现的接口
		Class[] interfaces = targetObject.getClass().getInterfaces();
		//参数3：调用处理器
		InvocationHandler invocationHandler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//执行前置增强
				if (beforeAdvice != null) {
					beforeAdvice.before();
				}

				//执行目标方法
				Object targetMethodResult = method.invoke(targetObject, args);

				//执行后置增强
				if (afterAdvice != null) {
					afterAdvice.after();
				}

				return targetMethodResult;
			}
		};

		//调用Proxy的newProxyInstance()方法来创建代理对象!!!
		Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);

		return proxyInstance;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	public BeforeAdvice getBeforeAdvice() {
		return beforeAdvice;
	}

	public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}

	public AfterAdvice getAfterAdvice() {
		return afterAdvice;
	}

	public void setAfterAdvice(AfterAdvice afterAdvice) {
		this.afterAdvice = afterAdvice;
	}
}
