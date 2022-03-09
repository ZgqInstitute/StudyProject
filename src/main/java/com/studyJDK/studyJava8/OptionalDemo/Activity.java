package com.studyJDK.studyJava8.OptionalDemo;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description:
 */
public class Activity {

    private Integer activityId;

    private String name;

    private Boolean isPro;


    public Activity(Integer activityId, String name, Boolean isPro) {
        this.activityId = activityId;
        this.name = name;
        this.isPro = isPro;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Boolean getPro() {
        return isPro;
    }

    public void setPro(Boolean pro) {
        isPro = pro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
