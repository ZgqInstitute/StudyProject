package com.studyFrame.springDemo.circularDependencies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class ConfigClass {

	@Bean
	public A a(){
		return new A();
	}

	@Bean
	public B b(){
		return new B();
	}

	@Bean
	public AspectClass aspectClass(){
		return new AspectClass();
	}

}
