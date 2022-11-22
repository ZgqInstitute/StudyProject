package com.studyDesignPattern.observerDemo.commonObserver.listener;

import com.studyDesignPattern.observerDemo.commonObserver.event.EventDemo;

/**
 * 观察者就是监听器（这个接口相当于spring中的ApplicationListener<E extends ApplicationEvent>）
 */
public interface ObserverListener {
    /**
     * 该方法相当于 ApplicationListener 接口的onApplicationEvent()方法
     * @param event 参数object就是事件Event，事件Event是由事件源传给监听器的
     */
    void update(Object event);
}
