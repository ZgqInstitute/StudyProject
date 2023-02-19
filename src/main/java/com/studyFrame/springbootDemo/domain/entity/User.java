package com.studyFrame.springbootDemo.domain.entity;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String name;

    private int age;

    private String school;

    private Integer sex;

    private int money;
}
