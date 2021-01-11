package com.studyDesignPattern.strategyDemo;

/**
 * 普通僵尸 继承 僵尸
 */
public class NormalZombie extends Zombie{

    public NormalZombie(){
        super(new MoveType01(),new AttackType01());
    }

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
