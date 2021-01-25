package com.studyFrame.springDemo.circularDependencies;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestClass extends DefaultListableBeanFactory {
	@Test
	public void testMethod(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springTestCircleRelyOn.xml");
		A bean = applicationContext.getBean(A.class);
		bean.testAOP();


	}
}
