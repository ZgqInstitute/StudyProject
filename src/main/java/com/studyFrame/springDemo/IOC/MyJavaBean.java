package com.studyFrame.springDemo.IOC;

import org.springframework.beans.factory.InitializingBean;


public class MyJavaBean implements InitializingBean {
	private String desc;
	private String remark;

	public MyJavaBean(){
		System.out.println("构造函数执行: " + getDesc() +"    "+ getRemark());
	}

	public void myInitMethod() {
		System.out.println("调用initMethod方法");
	}

//	@PostConstruct
//	public void JSR250(){
//		System.out.println("JSR250");
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("bean通过实现InitializingBean接口方式来初始化");
		this.desc = "bean通过实现InitializingBean接口方式填充《描述》属性";
		this.remark = "bean通过实现InitializingBean接口方式填充《备注》属性";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[描述：").append(desc);
		builder.append("， 备注：").append(remark).append("]");
		return builder.toString();
	}


	public String getDesc() {
		return desc;
	}

//	public void setDesc(String desc) {
//		System.out.println("调用setDesc()方法：" + getDesc());
//		this.desc = desc;
//		System.out.println("调用setDesc()方法：" + getDesc());
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		System.out.println("调用setRemark()方法：" +  getRemark());
		this.remark = remark;
		System.out.println("调用setRemark()方法：" +  getRemark());
	}
}
