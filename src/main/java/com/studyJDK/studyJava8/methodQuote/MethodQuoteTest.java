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
        Function<Person, String> a1 = (Person p) -> {
            String s = p.method01();
            return s;
        };
        Function<Person, String> a2 = Person::method01;


        Function<String, String> b1 = (String s) -> {
            String s1 = s + "nba";
            return s1;
        };
        Function<String, String> b2 = Person::method02;

    }


}
