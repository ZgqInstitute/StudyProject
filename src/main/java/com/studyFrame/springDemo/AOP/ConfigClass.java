package com.studyFrame.springDemo.AOP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class ConfigClass {

	@Bean
	public TargetClass targetClass(){
		return new TargetClass();
	}

	@Bean
	public AspectClass aspectClass(){
		return new AspectClass();
	}


}
