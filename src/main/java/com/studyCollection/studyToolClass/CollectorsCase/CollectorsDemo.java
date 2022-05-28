package com.studyCollection.studyToolClass.CollectorsCase;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class CollectorsDemo {
    private Person person1 = new Person("a1", 12, "w", "北京");
    private Person person2 = new Person("a2", 33, "w", "上海");
    private Person person3 = new Person("a3", 5, "m", "北京");
    private Person person4 = new Person("a4", 16, "w", "上海");
    private Person person5 = new Person("a5", 12, "w", "北京");
    private Person person6 = new Person("a6", 24, "w", "杭州");
    private List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6);

    /**
     * 使用Collectors的toMap方法将List转Map
     */
    @Test
    public void toMapDemo(){
        /**
         * Collectors.toMap方法有三种实现方式
         * 方式一：
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
         * Collectors.toMap方法有三种实现方式
         * 方式二：toMap(Function<? super T, ? extends K> keyMapper,
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
         * Collectors.toMap方法有三种实现方式
         * 方式三：toMap(Function<? super T, ? extends K> keyMapper,
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
     * 使用Collectors的groupingBy方法将List转Map
     */
    @Test
    public void groupingByDemo(){
        /**
         * Collectors.groupingBy方法有三种实现方式
         * 方式一：
         *
         */
        Map<Integer, List<Person>> groupingBy01 = list.stream().collect(Collectors.groupingBy(x -> x.getAge()));

        /**
         * Collectors.groupingBy方法有三种实现方式
         * 方式二：
         *
         */
        Map<Integer, Long> groupingBy02 = list.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));

        /**
         * Collectors.groupingBy方法有三种实现方式
         * 方式三：
         *
         */

    }


}
