package com.dataStructureAlgorithm.studyLRU;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现LRU，用LinkedHashMap实现
 */
public class LRUCacheByLinkedHashMap extends LinkedHashMap {
    private int capacity;
    private LRUCacheByLinkedHashMap(int capacity){
        super(capacity,0.75F,true);
        this.capacity = capacity;
    }


    public boolean removeEldestEntry(Map.Entry entry){
        return size()>capacity;
    }

    public static void main(String[] args) {
        LRUCacheByLinkedHashMap lru = new LRUCacheByLinkedHashMap(3);
        lru.put("a","A");
        lru.put("b","B");
        lru.put("c","C");
        System.out.println(lru);

        lru.put("d","D");
        System.out.println(lru);
    }
}
