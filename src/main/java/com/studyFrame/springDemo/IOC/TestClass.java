package com.studyFrame.springDemo.IOC;

import com.studyFrame.springDemo.IOC.MyJavaBean;
import org.junit.Test;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestClass extends InstantiationAwareBeanPostProcessorAdapter {
	@Test
	public void testMethod(){
//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springConfigFile.xml");
		MyJavaBean myJavaBean = applicationContext.getBean(MyJavaBean.class);
		System.out.println("描述：" + myJavaBean.getDesc());
		System.out.println("备注：" + myJavaBean.getRemark());


	}
}
