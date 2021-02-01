package com.studyFrame.springDemo.IOC;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestClass   {
	@Test
	public void testMethod(){
//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springTestCircleRelyOn.xml");
		MyJavaBean myJavaBean = applicationContext.getBean(MyJavaBean.class);
		System.out.println("描述：" + myJavaBean.getDesc());
		System.out.println("备注：" + myJavaBean.getRemark());


	}
}
