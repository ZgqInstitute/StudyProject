package com.studyFrame.springDemo.DI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

	@Bean
	public China china(){
		return new China("中国");
	}

	@Bean
	public CountryServiceImpl countryServiceImpl(){
		return new CountryServiceImpl();
	}

}
