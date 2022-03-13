package com.studyDesignPattern.strategyDemo.demo01;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description: 策略3
 */
public class RedPen implements Strategy {
    @Override
    public void draw() {
        System.out.println("红色");
    }
}
