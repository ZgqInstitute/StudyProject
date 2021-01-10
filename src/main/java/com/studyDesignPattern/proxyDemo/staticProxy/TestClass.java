package com.studyDesignPattern.proxyDemo.staticProxy;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod(){
		//创建被代理对象(目标对象)
		TeacherDao teacherDao = new TeacherDao();
		//创建代理对象
		TeacherDaoProxy teacherDaoProxy = new TeacherDaoProxy(teacherDao);
		/**
		 * 调用代理对象(目标对象)的目标方法；
		 * 《会实现在原有方法的基础上实现了增强功能》
		 *     代理老师讲课      -->增强方法
		 *     被代理老师（目标对象）讲课   -->目标方法
		 */
		teacherDaoProxy.teach();
	}
}
