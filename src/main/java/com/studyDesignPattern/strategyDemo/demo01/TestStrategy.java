package com.studyDesignPattern.strategyDemo.demo01;

import org.junit.Test;

/**
 * @auther: guangquan_zhu
 * @Date:
 * @Description: 测试类
 */
public class TestStrategy {

    @Test
    public void test(){
        RedPen redPen = new RedPen();
        BluePen bluePen = new BluePen();
        GreenPen greenPen = new GreenPen();

        Context context = new Context();

        // 切换成策略1
        context.setStrategy(bluePen);
        context.executeStrategy();

        // 切换成策略2
        context.setStrategy(greenPen);
        context.executeStrategy();

        // 切换成策略3
        context.setStrategy(redPen);
        context.executeStrategy();
    }


}
