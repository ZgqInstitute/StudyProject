package com.studyJDKAPI.studyJavaBase.reflect;



public class Person {

	private String name;
	private int age;


	public Person() {

	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}


	public void method01() {
		System.out.println("method01方法");
	}

	public void method02(String s1,String s2) {
		System.out.println("method02方法" + s1 + s2);
	}

	private void method03(){
		System.out.println("method03私有方法");
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
