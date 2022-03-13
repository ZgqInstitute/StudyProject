package com.studyDesignPattern.strategyDemo.demo02;


/**
 * 僵尸抽象类（定义策略）
 */
public abstract class Zombie {

    Moveable moveable;
    Attackable attackable;

    abstract void display();
    abstract void move();
    abstract void attack();

    public Zombie(Moveable moveable, Attackable attackable) {
        this.moveable = moveable;
        this.attackable = attackable;
    }

    public Moveable getMoveable() {
        return moveable;
    }

    public void setMoveable(Moveable moveable) {
        this.moveable = moveable;
    }

    public Attackable getAttackable() {
        return attackable;
    }

    public void setAttackable(Attackable attackable) {
        this.attackable = attackable;
    }
}
