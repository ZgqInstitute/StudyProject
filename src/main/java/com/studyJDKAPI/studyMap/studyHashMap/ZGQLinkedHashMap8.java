package com.studyJDKAPI.studyMap.studyHashMap;


public class ZGQLinkedHashMap8<K,V> extends ZGQHashMap8<K,V>{
	static class Entry<K,V> extends ZGQHashMap8.Node<K,V> {
		Entry<K,V> before, after;
		Entry(int hash, K key, V value, Node<K,V> next) {
			super(hash, key, value, next);
		}
	}
}
