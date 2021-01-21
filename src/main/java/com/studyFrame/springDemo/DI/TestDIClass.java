package com.studyFrame.springDemo.DI;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestDIClass {

	@Test
	public void testMethod(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
		CountryServiceImpl countryServiceImpl = applicationContext.getBean(CountryServiceImpl.class);
		countryServiceImpl.printCountryName();

	}
}
