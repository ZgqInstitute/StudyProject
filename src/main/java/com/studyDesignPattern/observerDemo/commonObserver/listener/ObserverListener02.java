package com.studyDesignPattern.observerDemo.commonObserver.listener;

/**
 * 观察者02对象
 * 观察者就是监听器xxxListener
 */
public class ObserverListener02 implements ObserverListener {
    @Override
    public void update(Object object) {
        System.out.println("观察者02对象收到主题的通知 " + object + "  ，处理自己的业务。。。");
    }
}
