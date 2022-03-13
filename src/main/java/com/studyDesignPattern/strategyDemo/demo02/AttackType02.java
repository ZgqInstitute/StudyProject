package com.studyDesignPattern.strategyDemo.demo02;


/**
 * 僵尸的攻击方式2
 */
public class AttackType02 implements Attackable {
    @Override
    public void attack() {
        System.out.println("僵尸攻击方式2");
    }
}