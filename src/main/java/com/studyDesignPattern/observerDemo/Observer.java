package com.studyDesignPattern.observerDemo;

/**
 * 观察者（这个接口相当于spring中的ApplicationListener<E extends ApplicationEvent>）
 */
public interface Observer {
    /**
     * 该方法相当于 ApplicationListener 接口的onApplicationEvent()方法
     */
    void update(Object object);
}
