package com.studyCollection.studyList.apiList;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class ListApiTest {

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
