package com.studyDesignPattern.proxyDemo.staticProxy;


/**
 * 代理对象与目标对象需要共同实现的接口
 *
 *  说明：静态代理的优缺点
 *     优点：在不修改目标对象的前提下，可以通过代理对象对目标对象进行功能扩展
 *     缺点：由于代理对象与目标对象需要实现相同的接口，一旦接口增加了方法，那么目标对象与代理对象都需要修改。
 * */
public interface ITeacherDao {

	/**
	 * 目标方法（需要被增强的方法）
	 * */
	void teach();
}
