package com.studyDesignPattern.proxyDemo.staticProxy;

/**
 * 被代理的对象（目标对象）
 * */
public class TeacherDao implements ITeacherDao{
	@Override
	public void teach() {
		System.out.println("被代理老师（目标对象）讲课");
	}
}
