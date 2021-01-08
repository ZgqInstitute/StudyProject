package com.studyDesignPattern.decoratorDemo;

public class Coffee extends Drink{

    @Override
    public float cost() {
        return super.getPrice();
    }
}
