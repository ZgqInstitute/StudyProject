package com.studyFrame.springDemo.IOC.testBeanDefinitionRegistryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

		System.out.println("MyBeanDefinitionRegistryPostProcessor======postProcessBeanDefinitionRegistry()方法执行");
		System.out.println("添加dog之前容器中BeanDefinition的数量 = " + registry.getBeanDefinitionCount());
		/*
		 * 向容器中增加BeanDefinition
		 */
		//创建Dog的BeanDefinition
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Dog.class);
		//将Dog的BeanDefinition注册到容器中
		registry.registerBeanDefinition("jinmao", rootBeanDefinition);
		System.out.println("添加dog之后容器中BeanDefinition的数量 = " + registry.getBeanDefinitionCount());

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanDefinitionRegistryPostProcessor----->postProcessBeanFactory()方法执行");
		//获取容器中所有BeanDefinition的个数
		int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
		System.out.println("现在容器中所有BeanDefinition的个数 = " + beanDefinitionCount);


	}
}
