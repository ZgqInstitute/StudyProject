package com.studyFrame.springDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

	@Bean
	public FactoryBeanDemo factoryBeanDemo01(){
		return new FactoryBeanDemo();
	}

}
