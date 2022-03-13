package com.studyDesignPattern.strategyDemo.demo01;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description: 策略2
 */
public class GreenPen implements Strategy {
    @Override
    public void draw() {
        System.out.println("绿色");
    }
}
