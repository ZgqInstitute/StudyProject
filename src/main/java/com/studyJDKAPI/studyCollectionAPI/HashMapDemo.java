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

    @Test
    public void containsKeyTest() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Google");
        map.put(2, "Runoob");
        map.put(3, "Taobao");

        System.out.println(map.containsKey(1));
        System.out.println(map.containsKey(4));
    }

    /**
     * computeIfAbsent() 方法对 hashMap 中指定 key 的值进行重新计算，如果不存在这个 key，则添加到 hashMap 中。
     * computeIfAbsent()的返回值：如果 key 对应的 value 不存在，则使用获取 remappingFunction 重新计算后的值，
     *                            并保存为该 key 的 value，否则返回 value。
     */
    @Test
    public void computeIfAbsentTest(){
        /*
         * 情况1：当使用computeIfAbsent()方法添加不存在的key
         */
        HashMap<String, Integer> prices = new HashMap<>();
        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Pant", 150);
        System.out.println("HashMap: " + prices);//HashMap: {Pant=150, Bag=300, Shoes=200}

        // 添加map中不存在的key
        int shirtPrice = prices.computeIfAbsent("Shirt", key -> 280);//返回值就是value=280
        System.out.println("Price of Shirt: " + shirtPrice);//Price of Shirt: 280

        // 输出更新后的HashMap
        System.out.println("Updated HashMap: " + prices);//Updated HashMap: {Pant=150, Shirt=280, Bag=300, Shoes=200}



        /*
         * 情况2：当使用computeIfAbsent()方法添加已经存在的key
         */
        HashMap<String, Integer> prices02 = new HashMap<>();
        prices02.put("Shoes", 180);
        prices02.put("Bag", 300);
        prices02.put("Pant", 150);
        System.out.println("HashMap: " + prices02);//HashMap: {Pant=150, Bag=300, Shoes=180}

        // map中已经有Shoes的映射关系，并没有使用新值280
        int shoePrice = prices02.computeIfAbsent("Shoes", (key) -> 280);
        System.out.println("Price of Shoes: " + shoePrice);//Price of Shoes: 180

        // 输出更新后的 HashMap
        System.out.println("Updated HashMap: " + prices02);//Updated HashMap: {Pant=150, Bag=300, Shoes=180}
    }

    /**
     * putIfAbsent()方法会先判断指定的键（key）是否存在，不存在则将键/值对插入到 HashMap 中。
     * putIfAbsent()方法返回值：
     *     如果所指定的 key 已经在 HashMap 中存在，返回和这个 key 值对应的 value；
     *     如果所指定的 key 不在 HashMap 中存在，则返回 null；
     *     注意：如果指定 key 之前已经和一个 null 值相关联了 ，则该方法也返回 null。
     */
    @Test
    public void putIfAbsentTest(){
        HashMap<String, Integer> prices = new HashMap<>();
        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Pant", 150);
        System.out.println(prices);//{Pant=150, Bag=300, Shoes=200}

        // put的key在map中不存在
        Integer book = prices.putIfAbsent("book", 100);
        System.out.println(book);//null
        System.out.println(prices);//{Pant=150, book=100, Bag=300, Shoes=200}

        // put的key在map中已经存在
        Integer shoes = prices.putIfAbsent("Shoes", 110);
        System.out.println(shoes);//200
        System.out.println(prices);//{Pant=150, book=100, Bag=300, Shoes=200} shoes的value还是原来的200
    }



}
