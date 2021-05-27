package com.studyFrame.springDemo.DI;

public class China {
	private String name;

	public China(){
		System.out.println("执行Chain类的构造函数……………………………………………………………………………………………………");
	}

	public China(String name) {
		System.out.println("执行Chain类的有参构造函数……………………………………………………………………………………………………");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
