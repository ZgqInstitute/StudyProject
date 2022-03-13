package com.studyDesignPattern.strategyDemo.demo02;


/**
 * 僵尸的攻击方式1
 */
public class AttackType01 implements Attackable {
    @Override
    public void attack() {
        System.out.println("僵尸攻击方式1");
    }
}
