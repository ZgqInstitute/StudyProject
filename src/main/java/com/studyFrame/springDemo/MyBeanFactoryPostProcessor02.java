package com.studyFrame.springDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor02 implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("执行MyBeanFactoryPostProcessor的postProcessBeanFactory()方法开始");

		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person01");

		System.out.println("修改容器中bean的name属性");

		MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
		propertyValues.add("name","zhaoliu");
	}
}
