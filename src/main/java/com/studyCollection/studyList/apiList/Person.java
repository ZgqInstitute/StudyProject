package com.studyCollection.studyList.apiList;

import java.time.LocalDateTime;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }
}
