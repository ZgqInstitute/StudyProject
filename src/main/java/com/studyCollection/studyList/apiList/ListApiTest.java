package com.studyCollection.studyList.apiList;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class ListApiTest {

    /**
     * List转Map
     */
    @Test
    public void ListToMapTest(){
        Person person1 = new Person("a1", 12, "w", "北京");
        Person person2 = new Person("a2", 33, "w", "上海");
        Person person3 = new Person("a3", 5, "m", "北京");
        Person person4 = new Person("a4", 16, "w", "上海");
        Person person5 = new Person("a5", 12, "w", "北京");
        Person person6 = new Person("a6", 24, "w", "杭州");


        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6);

        /**
         * 使用Collectors.groupingBy实现
         *
         */
        Map<Integer, List<Person>> groupingBy01 = list.stream().collect(Collectors.groupingBy(x -> x.getAge(), Collectors.toList()));
        Map<Integer, List<Person>> groupingBy02 = list.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.toList()));

        /**
         * Collectors.toMap方法三种实现
         * 实现一：
         *        toMap(Function<? super T, ? extends K> keyMapper,
         *                  Function<? super T, ? extends U> valueMapper)
         * 说明：
         *       使用这种实现方式key不能重复，一旦key重复将报java.lang.IllegalStateException: Duplicate key异常
         */
        Map<String, Person> toMap01 = list.stream().collect(Collectors.toMap(
                Person::getName,
                x -> x
//                Function.identity()  注：这种方式也是返回本身，逼格比x -> x要高
        ));

        /**
         * Collectors.toMap方法三种实现
         * 实现二：toMap(Function<? super T, ? extends K> keyMapper,
         *                                     Function<? super T, ? extends U> valueMapper,
         *                                     BinaryOperator<U> mergeFunction)
         * 说明：
         *      使用这种方式允许key重复，当key重复时将使用参数三的处理方式
         */
        Map<Integer, Person> toMap02_1 = list.stream().collect(Collectors.toMap(
                Person::getAge,
                Function.identity(),
                // 当key重复时，直接使用原来key对应的value。x和y对应的都是value值
                (x, y) -> x
        ));
        Map<String, List<Person>> toMap02_2 = list.stream().collect(Collectors.toMap(
                x -> x.getAge() + x.getSex(),
                // 因为ArrayList只有构造函数ArrayList(Collection<? extends E> c)，没有ArrayList(Person)构造
                a -> new ArrayList<>(Collections.singletonList(a)),
                // 当key重复时，value为所有相同key对应value的和。a1和a2对应的都是value值
                (a1, a2) -> {
                    a1.addAll(a2);
                    return a1;
                }
        ));

        /**
         * Collectors.toMap方法三种实现
         * 实现三：toMap(Function<? super T, ? extends K> keyMapper,
         *                                 Function<? super T, ? extends U> valueMapper,
         *                                 BinaryOperator<U> mergeFunction,
         *                                 Supplier<M> mapSupplier)
         * 说明：
         *     当我们想要返回LinkedHashMap时，就使用带四个参数的toMap
         */
        LinkedHashMap<Integer, Person> toMap03 = list.stream().collect(Collectors.toMap(
                Person::getAge,
                Function.identity(),
                // 当key重复时，直接使用原来key对应的value。x和y对应的都是value值
                (x, y) -> x,
                LinkedHashMap::new
        ));

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
