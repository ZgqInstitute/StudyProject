package com.studyCollection.studyMap.studyHashMap;


import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	private int capacity;//缓存容量
	public LRUCache(int capacity) {
		//false，所有的Entry按照插入的顺序排列
        //true，所有的Entry按照访问的顺序排列
		super(capacity, 0.75F, false);
		this.capacity = capacity;
	}
	/**
	 * 当缓存容量满时，以删除的方式腾出位置
	 */
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry eldest) {
		return size() > capacity;
	}

	public static void main(String[] args) {
		LRUCache lruCache = new LRUCache(3);
		lruCache.put(1, "a");
		lruCache.put(2, "b");
		lruCache.put(3, "c");
		System.out.println(lruCache);

		lruCache.put(2, "d");
		System.out.println(lruCache);
	}
}
