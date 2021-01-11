package com.studyDesignPattern.strategyDemo;

/**
 * 僵尸的移动方式2
 */
public class MoveType02 implements Moveable {
    @Override
    public void move() {
        System.out.println("僵尸移动方法2");
    }
}
