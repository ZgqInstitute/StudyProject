package com.studyCollection.studyList.studyLinkedList;

import org.junit.Test;

import java.util.LinkedList;


public class TestLinkedListDemo {

	@Test
	public void testMethod() {
		LinkedList<String> linkedList = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			linkedList.add("zgq" + i);
		}

		for (String s : linkedList) {
			System.out.println(s);
		}
	}

}
