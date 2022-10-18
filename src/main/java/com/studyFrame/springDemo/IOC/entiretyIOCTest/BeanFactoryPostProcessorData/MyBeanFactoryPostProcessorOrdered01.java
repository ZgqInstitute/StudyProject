package com.studyFrame.springDemo.IOC.entiretyIOCTest.BeanFactoryPostProcessorData;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;


public class MyBeanFactoryPostProcessorOrdered01 implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("调用MyBeanFactoryPostProcessor的postProcessBeanFactory()方法。。。。。。。。Ordered01");
        BeanDefinition bd = beanFactory.getBeanDefinition("myJavaBean");
        MutablePropertyValues pv = bd.getPropertyValues();

        System.out.println("通过BeanFactoryPostProcessor方式修改remark属性&&&&&&&&&&Ordered01");
        bd.getPropertyValues().add("remark", "6666");

        System.out.println();

        System.out.println("通过BeanFactoryPostProcessor方式修改desc属性////////Ordered01");
        bd.getPropertyValues().add("desc", "7777");

        if (pv.contains("remark")) {
            System.out.println("通过BeanFactoryPostProcessor方式修改remark属性#####Ordered01");
            pv.addPropertyValue("remark", "在BeanFactoryPostProcessor中修改之后的《备注》信息");
        }
        if (pv.contains("desc")) {
            System.out.println("通过BeanFactoryPostProcessor方式修改desc属性[[[[[[[[Ordered01");
            pv.addPropertyValue("desc", "在BeanFactoryPostProcessor中修改之后的《描述》信息");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}