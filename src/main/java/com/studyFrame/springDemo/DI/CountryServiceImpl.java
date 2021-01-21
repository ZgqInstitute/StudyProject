package com.studyFrame.springDemo.DI;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryServiceImpl  {

	@Autowired
	private China china;

	public void printCountryName() {
		System.out.println(china.getName());
	}
}
