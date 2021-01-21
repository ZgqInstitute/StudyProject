package com.studyFrame.springDemo.IOC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

	@Bean
	public Person person001(){
		return new Person();
	}

	@Bean
	public Home home001(){
		return new Home();
	}


}
