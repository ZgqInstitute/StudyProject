package com.studyFrame.springDemo.DI;

import org.springframework.beans.factory.annotation.Autowired;

public class CountryServiceImpl  {

	@Autowired
	private China china;

	public CountryServiceImpl(){
		System.out.println("执行构造函数##############################################################################################");
	}
//
//	@Autowired
//	public void testAutowiredAtMethod(){
//		System.out.println("@Autowired标注在方法上@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//	}
//
//	public void printCountryName() {
//		System.out.println(china.getName());
//	}
}
