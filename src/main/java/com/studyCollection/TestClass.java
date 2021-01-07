package com.studyCollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestClass {

	@Test
	public void testMethod(){
		List<Person> list01 = new ArrayList<>();
		list01.add(new Person("张三"));
		list01.add(new Person("李四"));
		list01.add(new Person("王五"));
		list01.add(new Person("造流"));

		List<Person> list02 = new ArrayList<>();
		list02.add(new Person("AAA"));
//		list02.addAll(new Person("BBB"));
		list02.addAll(list01);
	}
}
