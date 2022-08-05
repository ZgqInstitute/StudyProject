package com.studyJDKAPI.studyList.apiList;

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
     * 需求：对person按一下规则进行排序
     * 1-年龄从大到小
     * 2-身高从小到大
     * 3-越早出生放前面
     */
    @Test
    public void sortList() {
        Person person1 = new Person(60, 170, LocalDateTime.of(1992, 3, 13, 9, 43));
        Person person2 = new Person(12, 170, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person3 = new Person(60, 180, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person4 = new Person(60, 170, LocalDateTime.of(1994, 1, 13, 9, 43));
        Person person5 = new Person(12, 190, LocalDateTime.of(2018, 1, 13, 9, 43));
        Person person6 = new Person(12, 170, LocalDateTime.of(2022, 1, 13, 9, 43));

        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6);

        /**
         * 方法一：直接使用List的sort()方法
         */
        list.sort(xx());

        /**
         * 方法二：使用stream的sorted()方法
         */
        List<Person> collect = list.stream().sorted(xx()).collect(Collectors.toList());

        for (Person p : collect) {
            System.out.println(p);
        }
    }

    private Comparator<Person> xx() {
        return Comparator
                // 第一个排序字段如果要倒序的话直接写就行
                .comparing(Person::getAge).reversed()
                // 第二个排序字段如果要倒序的话要加Comparator.comparingInt()
                .thenComparing(Comparator.comparingInt(Person::getHeight).reversed())
                .thenComparing(Person::getBirth);
    }
}
