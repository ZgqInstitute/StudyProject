package com.studyDesignPattern.strategyDemo.demo01;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description: 策略1
 */
public class BluePen implements Strategy {
    @Override
    public void draw() {
        System.out.println("蓝色");
    }
}
