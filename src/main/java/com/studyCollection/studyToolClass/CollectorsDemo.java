package com.studyCollection.studyToolClass;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class CollectorsDemo {

    @Test
    public void toListDemo() {
        Person a1 = new Person("a1", 17);
        Person a2 = new Person("a2", 72);
        Person a3 = new Person("a3", 29);
        List<Person> list = Arrays.asList(a1, a2, a3);
        list.stream()
                .map(Person::getAge)
                .collect(Collectors.toList());
    }


}
