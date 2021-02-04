package com.studyFrame.springDemo.IOC.testBeanDefinitionRegistryPostProcessor;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestClass {
	@Test
	public void testMethod(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass02.class);


	}
}
