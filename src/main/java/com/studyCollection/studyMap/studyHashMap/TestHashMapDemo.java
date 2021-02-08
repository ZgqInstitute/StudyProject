package com.studyCollection.studyMap.studyHashMap;

import org.junit.Test;


import java.util.HashMap;


public class TestHashMapDemo {

	@Test
	public void testMethod() {

		HashMap<String, String> hashMap = new HashMap<>();

		for (int i = 0; i < 15; i++) {
			hashMap.put("zgq_key " + i, "zgq_value " + i);
		}

		hashMap.put("a","A");



	}

}
