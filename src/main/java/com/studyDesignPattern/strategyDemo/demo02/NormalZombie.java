package com.studyDesignPattern.strategyDemo.demo02;

/**
 * 普通僵尸 继承 僵尸
 */
public class NormalZombie extends Zombie {

    // 默认使用移动方式1  攻击方式1
    public NormalZombie(){
        super(new MoveType01(), new AttackType01());
    }

    // 通过构造函数来确定移动方式和攻击方式(确定算法)
    public NormalZombie(Moveable moveable, Attackable attackable) {
        super(moveable, attackable);
    }

    @Override
    public void display() {
        System.out.println("我是普通僵尸");
    }

    @Override
    void move() {
        moveable.move();
    }

    @Override
    void attack() {
        attackable.attack();
    }
}
