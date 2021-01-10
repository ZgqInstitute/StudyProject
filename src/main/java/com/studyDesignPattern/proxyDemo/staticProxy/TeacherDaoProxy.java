package com.studyDesignPattern.proxyDemo.staticProxy;

/**
 * 代理对象
 */
public class TeacherDaoProxy implements ITeacherDao {

	//用来接收构造函数中传过来的目标对象
	private ITeacherDao target;

	//通过构造函数来引入目标对象
	TeacherDaoProxy(ITeacherDao target) {
		this.target = target;
	}


	@Override
	public void teach() {
		System.out.println("代理老师讲课");
		target.teach();
	}
}
