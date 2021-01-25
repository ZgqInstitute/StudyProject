package com.studyFrame.springDemo.circularDependencies;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectClass {

	@Pointcut("execution(public void com.studyFrame.springDemo.circularDependencies.A.*(..))")
	public void pointCut(){	}

	@Before("pointCut()")
	public void targetMethodBefore(){
		System.out.println("目标方法执行前");
	}

}
