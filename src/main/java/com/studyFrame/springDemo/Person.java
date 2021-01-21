package com.studyFrame.springDemo;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {
	private String name;

	@Autowired
	private Home home;

	Person(){}

	Person(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", home=" + home +
				'}';
	}
}
