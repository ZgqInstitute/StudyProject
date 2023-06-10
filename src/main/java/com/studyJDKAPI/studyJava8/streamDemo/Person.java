package com.studyJDKAPI.studyJava8.streamDemo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
@Data
public class Person {

    private String name;
    private Integer age;
    private String sex;
    private String address;
    private Integer height;
    private LocalDateTime birth;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, Integer age, String sex, String address, Integer height, LocalDateTime birth) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.height = height;
        this.birth = birth;
    }

    public Person(String name, Integer age, String sex, String address) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", height=" + height +
                ", birth=" + birth +
                '}';
    }

}
