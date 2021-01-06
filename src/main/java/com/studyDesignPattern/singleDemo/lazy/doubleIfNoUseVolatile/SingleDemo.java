package com.studyDesignPattern.singleDemo.lazy.doubleIfNoUseVolatile;

public class SingleDemo {
	private static SingleDemo singleDemo;

	private SingleDemo() {
	}

	public synchronized static SingleDemo getInstance() {
		if (singleDemo == null) {
			synchronized (SingleDemo.class) {
				if (singleDemo == null) {
					/**
					 * 对象的创建总体可以分为以下三步：
					 *    1-在内存分配空间；
					 *    2-对象进行初始化；
					 *    3-将引用变量指向第一步分配的内存地址；
					 *注：若步骤2与步骤3发送指令重排，那么将发送线程安全问题，详细见多线程高级笔记。
					 */
					singleDemo = new SingleDemo();
				}
			}
		}
		return singleDemo;
	}
}
