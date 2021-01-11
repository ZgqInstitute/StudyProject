package com.studyDesignPattern.strategyDemo;

/**
 * 僵尸的移动方式1
 */
public class MoveType01 implements Moveable {
    @Override
    public void move() {
        System.out.println("僵尸移动方法1");
    }
}
