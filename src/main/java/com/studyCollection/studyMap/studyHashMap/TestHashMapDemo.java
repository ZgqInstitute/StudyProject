package com.studyCollection.studyMap.studyHashMap;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TestHashMapDemo {

	@Test
	public void testMethod() {
		HashMap<String, String> concurrentHashMap = new HashMap<>();

		concurrentHashMap.put("key_zgq","value_zgq");

		concurrentHashMap.size();

		concurrentHashMap.remove("key_zgq");

	}

}
