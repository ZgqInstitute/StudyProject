package com.studyJDKAPI.studyCollectionAPI;

import org.junit.Test;

import java.util.HashMap;

/**
 * 测试HashMap的api
 */
public class HashMapDemo {
    @Test
    public void getOrDefaultTest() {
        HashMap<Integer, String> sites = new HashMap<>();
        sites.put(1, "Google");
        sites.put(2, "Runoob");
        sites.put(3, "Taobao");
        System.out.println("sites HashMap: " + sites);

        // map集合中有key对应的值直接返回，没有的话返回默认值
        String value1 = sites.getOrDefault(1, "默认值");
        System.out.println("Value for key 1:  " + value1);

        String value2 = sites.getOrDefault(4, "默认值");
        System.out.println("Value for key 4: " + value2);
    }
}
