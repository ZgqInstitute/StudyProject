package com.studyJDKAPI.studyJavaBase.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class TestClass {

	@Test
	public void testMethod() throws Exception {
		Class personClass = Class.forName("com.studyJDKAPI.studyJavaBase.reflect.Person");
		Constructor constructor = personClass.getConstructor();
		Person o = (Person)constructor.newInstance();
		Method method01 = personClass.getMethod("method02", String.class, String.class);
		Class[] parameterTypes = method01.getParameterTypes();

		System.out.println(parameterTypes.length);
		System.out.println("-----------------------------");
		method01.invoke(o,"AAAA","ThreadLocalDemo");

	}
}
