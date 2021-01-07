package com.studyJava8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TestDemo {

	@Test
	public void testMethod() {
		Person person = new Person();
		ArrayList<Person> personList = new ArrayList<>();
		personList.add(new Person("aaa"));
		personList.add(new Person("bbb"));
		personList.add(new Person("ccc"));
		String collect = personList.stream().map(Person::getName).collect(Collectors.joining("--"));
		person.setName(collect);
		System.out.println(person.getName());
	}
}
