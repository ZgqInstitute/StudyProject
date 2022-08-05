package com.studyJDKAPI.studyJava8.OptionDemo;


import java.util.List;

/**
 * @auther: guangquan_zhu
 * @Date: 2021-1-1
 * @Description: 辅助类
 */
public class Student {
    private String name;
    private int age;
    private List<Activity> activityList;
    private Boolean man;

    public Student(){}
    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public Boolean getMan() {
        return man;
    }

    public void setMan(Boolean man) {
        this.man = man;
    }
}
