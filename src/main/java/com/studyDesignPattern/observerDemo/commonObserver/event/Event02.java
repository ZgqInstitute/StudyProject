package com.studyDesignPattern.observerDemo.commonObserver.event;

public class Event02 implements EventDemo{
    @Override
    public void eventOccurred() {
        System.out.println("Event02>>>>>>eventOccurred()");
    }
}