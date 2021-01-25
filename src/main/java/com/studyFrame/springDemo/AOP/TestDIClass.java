package com.studyFrame.springDemo.AOP;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestDIClass extends DefaultListableBeanFactory {

	@Test
	public void testMethod(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		//这个getBean()得到的代理对象是什么时候创建的？
		TargetClass bean = applicationContext.getBean(TargetClass.class);
//		TargetClass bean = applicationContext.getBean("targetClass");
		bean.targetMethod(2,1);

	}
}
