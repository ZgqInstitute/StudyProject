package com.studyDesignPattern.strategyDemo;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod() {

		Zombie zombie = new NormalZombie();
		zombie.display();
		zombie.move();
		zombie.attack();

		System.out.println("------------------------");

		zombie.setMoveable(new MoveType02());
		zombie.setAttackable(new AttackType02());
		zombie.move();
		zombie.attack();
	}
}
