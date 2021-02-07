package com.studyFrame.springDemo.DI;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestDIClass {

	@Test
	public void testMethod(){
//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("springTestCircleRelyOn.xml");
		China china = applicationContext.getBean(China.class);
		System.out.println(china.getName());

	}
}
