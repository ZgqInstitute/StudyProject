package com.studyCollection.studyMap.studyHashMap;

import org.junit.Test;
import java.util.concurrent.ConcurrentHashMap;

public class TestHashMapDemo {

	@Test
	public void testMethod() {
		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

		concurrentHashMap.put("key_zgq","value_zgq");

		concurrentHashMap.size();

		concurrentHashMap.remove("key_zgq");

	}

}
