package com.studyJDK.studyJava8.methodQuote;

import org.junit.Test;
import java.util.function.Function;



/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class MethodQuoteTest {

    @Test
    public void mode01() {
        /**
         * 下面两种情况都可以直接使用  类名::非静态的方法名
         *   method01、method02方法特点：都没有入参，但是都有返回值
         *   Function<T, R>接口中的R apply(T t)方法有2个泛型，T是入参，R是返回值
         *   使用方法引用的前提条件：
         *                          1-apply方法的入参要与method01、method02方法入参一致；
         *                          2-apply方法的返回值要与method01、method02方法的返回值一致
         *   这里比较特殊，当method01、method02方法本身没有入参，但是Function<T, R>接口中的R apply(T t)方法入参正好
         *                是method01、method02方法所在类(Person)，那么还是可以使用方法引用的，直接 类名::非静态方法名
         */
        Function<Person, String> a1 = (Person p) -> {
            String s = p.method01();
            return s;
        };
        Function<Person, String> a2 = Person::method01;

        Function<Person, Worker> b1 = (Person p) -> {
            Worker worker = p.method02();
            return worker;
        };
        Function<Person, Worker> b2 = Person::method02;

        /**
         * 直接使用： 类名::静态方法名
         */
        Function<String, String> c1 = (String s) -> {
            String s1 = s + "nba";
            return s1;
        };
        Function<String, String> c2 = Person::method03;


    }

}
