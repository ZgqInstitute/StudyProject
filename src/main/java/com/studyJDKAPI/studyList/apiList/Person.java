package com.studyJDKAPI.studyList.apiList;

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
    private Integer height;
    private LocalDateTime birth;

    public Person() {
    }

    public Person(Integer age, Integer height, LocalDateTime birth) {
        this.age = age;
        this.height = height;
        this.birth = birth;
    }

    public Person(String name, Integer age, String sex, Integer height, LocalDateTime birth) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.birth = birth;
    }

    public Person(String name, Integer age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", birth=" + birth +
                '}';
    }

}
