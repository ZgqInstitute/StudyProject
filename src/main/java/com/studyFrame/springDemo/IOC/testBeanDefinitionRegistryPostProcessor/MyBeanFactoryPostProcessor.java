package com.studyFrame.springDemo.IOC.testBeanDefinitionRegistryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanFactoryPostProcessor--->postProcessBeanFactory()方法执行");
		BeanDefinition bd = beanFactory.getBeanDefinition("jinmao");
		MutablePropertyValues mutablePropertyValues =  bd.getPropertyValues();
		if (mutablePropertyValues.contains("name")) {
			System.out.println("修改Dog的BeanDefinition信息666666666666666666666677777777777777777777777778888888888888888888888888888888");
			mutablePropertyValues.addPropertyValue("name", "哈士奇==============================================");
		}

	}
}
