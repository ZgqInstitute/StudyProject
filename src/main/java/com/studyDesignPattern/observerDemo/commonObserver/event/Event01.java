package com.studyDesignPattern.observerDemo.commonObserver.event;

public class Event01 implements EventDemo{
    @Override
    public void eventOccurred() {
        System.out.println("Event01>>>>>>eventOccurred()");
    }
}
