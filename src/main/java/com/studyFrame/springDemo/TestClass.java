package com.studyFrame.springDemo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestClass  {
	@Test
	public void testMethod(){
//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springConfigFile.xml");
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);

	}
}
