package com.studyFrame.springDemo.IOC.entiretyIOCTest;

import com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanFactoryPostProcessorData.MyBeanFactoryPostProcessor;
import com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanFactoryPostProcessorData.MyBeanFactoryPostProcessorOrdered01;
import com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanFactoryPostProcessorData.MyBeanFactoryPostProcessorOrdered02;
import com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanFactoryPostProcessorData.MyBeanFactoryPostProcessorPriorityOrdered;
import com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanPostProcessorData.MyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

//	@Bean
//	public Person person001(){
//		return new Person();
//	}

//	@Bean
//	public Home home001(){
//		return new Home();
//	}

	@Bean
	public MyJavaBean myJavaBean(){
		return new MyJavaBean();
	}

	@Bean
	public MyBeanFactoryPostProcessor myBeanFactoryPostProcessor(){
		return new MyBeanFactoryPostProcessor();
	}

	@Bean
	public MyBeanFactoryPostProcessorPriorityOrdered myBeanFactoryPostProcessorPriorityOrdered(){
		return new MyBeanFactoryPostProcessorPriorityOrdered();
	}

	@Bean
	public MyBeanFactoryPostProcessorOrdered01 myBeanFactoryPostProcessorOrdered(){
		return new MyBeanFactoryPostProcessorOrdered01();
	}

	@Bean
	public MyBeanFactoryPostProcessorOrdered02 myBeanFactoryPostProcessorOrdered02(){
		return new MyBeanFactoryPostProcessorOrdered02();
	}

	@Bean
	public MyBeanPostProcessor myBeanPostProcessor(){
		return new MyBeanPostProcessor();
	}

}
