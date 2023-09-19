package com.studyJDKAPI.studyIO.objectStream;

import java.io.Serializable;

public class Student implements Serializable {

    /**
     * 显示声明序列号，一定要写serialVersionUID，写别的没有用，写SerialVersionUID都没用
     * 若没有显示声明serialVersionUID，那么将name的修饰符从private改为public后，反序列化失败
     * 若有显示声明serialVersionUID，那么将name的修饰符从private改为public后，反序列化成功
     */
    private static final long serialVersionUID = 13455613297L;

    private String name;
    private Integer age;
    private Integer no;
    private String address;

    public Student(String name, Integer age, Integer no, String address) {
        this.name = name;
        this.age = age;
        this.no = no;
        this.address = address;
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

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
