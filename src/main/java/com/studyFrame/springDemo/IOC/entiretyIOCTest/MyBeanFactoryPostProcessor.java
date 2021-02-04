package com.studyFrame.springDemo.IOC.entiretyIOCTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用MyBeanFactoryPostProcessor的postProcessBeanFactory()方法");
		BeanDefinition bd = beanFactory.getBeanDefinition("myJavaBean");
		MutablePropertyValues pv =  bd.getPropertyValues();
		if (pv.contains("remark")) {
			pv.addPropertyValue("remark", "在BeanFactoryPostProcessor中修改之后的《备注》信息");
		}
		if (pv.contains("desc")) {
			pv.addPropertyValue("desc", "在BeanFactoryPostProcessor中修改之后的《描述》信息");
		}
	}
}
