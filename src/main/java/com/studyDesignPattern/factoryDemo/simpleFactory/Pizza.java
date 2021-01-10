package com.studyDesignPattern.factoryDemo.simpleFactory;

/**
 * 封装披萨的名字和制作过程
 * */
public abstract class Pizza {
	protected String name;

	//准备原材料，不同的披萨原材料不同，因此做成抽象方法
	public abstract void prepare();

	//烘烤
	public void bake() {
		System.out.println(name + "烘烤");
	}

	//切割
	public void cut() {
		System.out.println(name + "切割");
	}

	//打包
	public void box() {
		System.out.println(name + "打包");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
