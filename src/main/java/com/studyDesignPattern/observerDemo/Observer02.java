package com.studyDesignPattern.observerDemo;

/**
 * 观察者02对象
 */
public class Observer02 implements Observer {
    @Override
    public void update(Object object) {
        System.out.println("观察者02对象收到主题的通知    " + object + "    ，处理自己的业务。。。");
    }
}
