package com.studyCollection.studyList.apiList;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class ListApiTest {

    /**
     * 需求：性别 + 地址作为key，性别+地址相同的放在同一个集合
     */
    @Test
    public void ListToMapTest(){
        Person person1 = new Person("a1", 12, "w", "北京");
        Person person2 = new Person("a2", 33, "w", "上海");
        Person person3 = new Person("a3", 5, "m", "北京");
        Person person4 = new Person("a4", 16, "w", "上海");
        Person person5 = new Person("a5", 95, "m", "北京");
        Person person6 = new Person("a6", 24, "w", "杭州");
        Person person7 = new Person("a7", 18, "m", "北京");
        Person person8 = new Person("a8", 143, "w", "上海");
        Person person9 = new Person("a9", 18, "m", "北京");

        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6, person7, person8, person9);

        // 方法1
        Map<String, List<Person>> map1 = list.stream().collect(Collectors.groupingBy(x -> x.getSex() + x.getAddress(), Collectors.toList()));

        // 方法2
        Map<String, List<Person>> map2 = list.stream().collect(Collectors.toMap(
                x -> x.getSex() + x.getAddress(),
                a -> new ArrayList<>(Collections.singletonList(a)),
                (a1, a2) -> {
                    a1.addAll(a2);
                    return a1;
                }
        ));

        System.out.println();
    }

    /**
     * 需求：对person按一下规则进行排序
     *          1-年龄从大到小
     *          2-身高从小到大
     *          3-越早出生放前面
     *
     */
    @Test
    public void sortList(){
        Person person1 = new Person("a1", 60, "w", "北京",170, LocalDateTime.of(1992, 3, 13, 9, 43));
        Person person2 = new Person("a2", 12, "w", "北京",170, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person3 = new Person("a3", 60, "w", "上海",180, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person4 = new Person("a4", 60, "w", "天津",170, LocalDateTime.of(1994, 1, 13, 9, 43));
        Person person5 = new Person("a5", 12, "w", "北京",190, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person6 = new Person("a6", 12, "w", "厦门",170, LocalDateTime.of(2022, 1, 13, 9, 43));

        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6);

        list.sort(
                Comparator
                // 第一个排序字段如果要倒序的话直接写就行
                .comparing(Person::getAge).reversed()
                // 第二个排序字段如果要倒序的话要加Comparator.comparingInt()
                .thenComparing(Comparator.comparingInt(Person::getHeight).reversed())
                .thenComparing(Person::getBirth));

        System.out.println();
    }







}
