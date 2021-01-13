package com.studyDesignPattern.templateMethod;

import org.junit.Test;

public class TestClass {

	@Test
	public void testMethod() {
		AbstractTemplate abstractTemplate1 = new SubClass01();
		abstractTemplate1.commonOperation();

		AbstractTemplate abstractTemplate2 = new SubClass02();
		abstractTemplate2.commonOperation();
	}
}
