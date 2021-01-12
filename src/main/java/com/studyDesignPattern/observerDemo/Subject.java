package com.studyDesignPattern.observerDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题对象，被观察(监听)的对象；
 * 多个观察者对象会同时监听这个主题对象，当主题对象发生改变时，主题对象会调用notifyObserver()方法，通知到每一个观察者对象
 */
public class Subject {

    private List<Observer> observerList = new ArrayList<>();

    //主题对象添加监听对象
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    //主题对象移除监听对象
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 当主题对象发生变化时，用主题对象的该方法通知到每一个观察者对象
     */
    public void notifyObserver(Object object) {
        for (Observer observer : observerList) {
            observer.update(object);
        }
    }


}
