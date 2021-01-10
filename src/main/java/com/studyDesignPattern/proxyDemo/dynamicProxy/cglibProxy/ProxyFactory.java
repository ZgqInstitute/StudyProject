package com.studyDesignPattern.proxyDemo.dynamicProxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理工厂，需要实现MethodInterceptor
 */
public class ProxyFactory implements MethodInterceptor {

	private Object target;

	//通过构造器传入目标对象
	ProxyFactory(Object target) {
		this.target = target;
	}

	/**
	 * 通过该方法创建目标对象(target)的代理对象（注：目标对象的代理对象就是目标对象的子类对象）
	 */
	public Object getProxyInstance() {
		//创建一个工具类
		Enhancer enhancer = new Enhancer();
		//设置父类
		enhancer.setSuperclass(target.getClass());
		//设置回调函数(回调函数就是代理工厂类)
		enhancer.setCallback(this);
		//通过工具类创建代理对象
		Object proxyInstance = enhancer.create();
		return proxyInstance;
	}

	/**
	 * 重写MethodInterceptor接口的intercept方法，在该方法内调用目标对象的方法
	 * 该方法相当于jdk动态代理中的invoke()方法
	 */
	@Override
	public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("cglib动态代理在目标方法前执行。。。。");

		Object targetMethodReturn = method.invoke(target, args);

		System.out.println("cglib动态代理在目标方法后执行。。。。");

		return targetMethodReturn;
	}
}
