package com.studyJDKAPI.studyJava8.methodQuote;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String method01() {
        return "nba";
    }

    public Worker method02() {
        return new Worker();
    }

    public static String method03(String s) {
        String s1 = s + "nba";
        return s1;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
