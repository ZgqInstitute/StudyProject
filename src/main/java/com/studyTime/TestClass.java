package com.studyTime;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

	@Test
	public void testMethod(){
		List<Person> list = new ArrayList<>();
		list.add(new Person(12));
		list.add(new Person(16));
		list.add(new Person(22));
		list.add(new Person(78));

		Iterator<Person> iterator1 = list.iterator();

		while (iterator1.hasNext()) {
			System.out.println(iterator1.next().getAge());
		}

		System.out.println("--------------");
		List<Integer> collect = list.stream().map(Person::getAge).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		Iterator<Integer> iterator = collect.iterator();

		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}

	}
}
