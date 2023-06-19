package com.studyJDKAPI.studyCollectionAPI.sourceCodeArrayList;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class TestArrayListDemo {

	@Test
	public void testMethod() {
		ArrayList<String> lists = new ArrayList<>();
		Vector<String> vector = new Vector<>();
		vector.add("abc");


		for (int i = 0; i < 15; i++) {
			lists.add("zgq" + i);
		}

		Iterator<String> iterator = lists.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			if ("zgq5".equals(iterator.next())) {
				lists.add("NBA");
			}
		}

//		lists.forEach(t -> {
//			lists.add("NBA");
//			System.out.println(t);
//		});


	}
}
