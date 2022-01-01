package com.studyJDK.studyJava8.OptionalDemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * @auther: guangquan_zhu
 * @Date: 2022-1-1
 * @Description: 学习optional
 */
public class OptionalTest {

    @Test
    public void testOf1() {
        Optional<Student> s = Optional.of(new Student("aa", 12));
        Student student = s.get();
        System.out.println(student);
    }

    /**
     * of()方法是创建一个Optional容器实例，不能构建空的Optional
     * 注：of()方法的参数若为null，将报空指针，也就是说不能通过of()方法来创建空的Optional实例，
     * 但是可以通过empty()方法来创建空的Optional实例
     */
    @Test
    public void testOf() {
        Optional<Student> s = Optional.of(new Student("aa", 12));
        Student student = s.get();
        System.out.println(student);
    }

    /**
     * 可以通过empty()方法创建空的Optional实例
     */
    @Test
    public void testEmpty() {
        Optional<Student> s = Optional.empty();
        Student student = s.get();
        System.out.println(student);
    }

    /**
     * ofNullable()方法的意思是：当参数为null就创建空的Optional实例，若参数不为null就创建Optional实例
     */
    @Test
    public void testOfNullable() {
        Optional<Student> s = Optional.ofNullable(new Student("ww", 12));
        Student student = s.get();
        System.out.println(student);
    }

    /**
     * 当使用OfNullable创建一个Optional实例时，不能确定Optional容器内是否有值，当Optional容器内没有值时调用了get()方法会报错
     * 所以可以先通过isPresent()方法来判断Optional容器内是否有值，若有值再调用get()
     */
    @Test
    public void testIsPresent() {
        Optional<Student> s = Optional.ofNullable(null);
        if (s.isPresent()) {
            Student student = s.get();
            System.out.println(student);
        }
    }

    /**
     * 当使用Optional.ofNullable()方式来创建Optional容器时，容器内可能有值，也可能没有值，
     * 可以使用orElse()方法，当Optional容器内有值，就返回Optional容器内的值；
     * 当Optional容器内没有值，就返回orElse()参数内的值；
     */
    @Test
    public void testOrElse() {
        Optional<Student> s = Optional.ofNullable(new Student("p1", 10));
        Student pp = s.orElse(new Student("p2", 12));
        System.out.println(pp);
    }

    /**
     * 当使用Optional.ofNullable()方式来创建Optional容器时，容器内可能有值，也可能没有值，
     * 可以使用orElseGet(Supplier s)方法，当Optional容器内有值，就返回Optional容器内的值；
     * 当Optional容器内没有值，就返回以orElseGet()方法参数方式获取的值；
     * <p>
     * 说明：使用orElse()方法来做兜底的话，兜底对象只能写死；
     * 使用orElseGet()方法来做兜底的话，兜底对象可以动态实现；
     */
    @Test
    public void testOrElseGet() {
        Optional<Student> s = Optional.ofNullable(null);
        Student pp = s.orElseGet(() -> new Student("p3", 17));
        System.out.println(pp);
    }

    /**
     * 当使用Optional.ofNullable()方式来创建Optional容器时，容器内可能有值，也可能没有值，
     * 可以使用map()方法：当Optional容器内有值，就将Optional容器内的值使用到map()方法中的函数，并返回Optional，Optional的泛型为map()方法内函数的返回值
     * 当Optional容器内没有值，就返回Optional.empty()；
     */
    @Test
    public void testMap() {
        Optional<Student> s = Optional.ofNullable(new Student("p1", 10));
        Optional<String> name = s.map(x -> x.getName());
        if (name.isPresent()) {
            System.out.println(name.get());
        }
    }

    /**
     * 当使用Optional.ofNullable()方式来创建Optional容器时，容器内可能有值，也可能没有值，
     * 说明：flatMap()与map()的区别是：flatMap()必须返回Optional
     */
    @Test
    public void testFlatMap() {
        Optional<Student> s = Optional.ofNullable(new Student("p1", 10));
        // flatMap()必须返回Optional
        Optional<String> name = s.flatMap(x -> Optional.of(x.getName()));
        if (name.isPresent()) {
            System.out.println(name.get());
        }
    }

    @Test
    public void tt() {
        Student student1 = new Student();
        student1.setName("aa");
        student1.setAge(1);

        Student student2 = new Student();
        student2.setName("bb");
        student2.setAge(9);

        Student student3 = new Student();
        student3.setName("cc");
        student3.setAge(2);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        System.out.println("xxx");

        for(ListIterator<Student> it = studentList.listIterator(); it.hasNext();){
            Student next = it.next();
            if(next.getAge()==9){
                next.setAge(1);

                Student s = new Student();
                s.setName(next.getName());
                s.setAge(8);
                it.add(s);
            }

        }

        System.out.println("zz");

    }

}
