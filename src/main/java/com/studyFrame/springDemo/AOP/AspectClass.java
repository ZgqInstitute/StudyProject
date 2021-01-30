package com.studyFrame.springDemo.AOP;

import org.aspectj.lang.annotation.*;

@Aspect
public class AspectClass {

	@Pointcut("execution(public int com.studyFrame.springDemo.AOP.TargetClass.*(..))")
	public void pointCut(){	}

	@Before("pointCut()")
	public void targetMethodBefore(){
		System.out.println("目标方法执行前");
	}

	@After("pointCut()")
	public void targetMethodAfter(){
		System.out.println("目标方法执行结束");
	}


}
