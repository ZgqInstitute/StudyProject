package com.studyFrame.springbootDemo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ZGQConfigAspect {

    @Around("@annotation(zgqAnnotation)")
    public Object zgqTestAop(ProceedingJoinPoint pjp, ZGQAnnotation zgqAnnotation) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object proceed = pjp.proceed();

        // 得到方法签名，哪个接口被调用，那么method就是哪个接口
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // 看下method方法上是否加了自定义注解
        ZGQAnnotation annotation = method.getAnnotation(ZGQAnnotation.class);
        // 创建StringBuffer，用来拼接目标接口的形参与实参
        StringBuffer stringBuffer = new StringBuffer();
        if (annotation != null) {
            // 得到目标方法上的实参
            Object[] args = pjp.getArgs();
            // 得到目标方法上的形参
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            // parameterNames数组存放的是目标方法的形参名
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                stringBuffer.append(parameterNames[i] + " = " + args[i].toString());
            }
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        // 目标接口所在的类名
        String targetClassName = pjp.getTarget().getClass().getName();
        // 目标接口所在的方法名
        String targetMethodName = signature.getMethod().getName();

        /**
         * 这个就是需要打印的日志
         */
        log.info("接口耗时: " + time + ", " +
                "接口方法名: " + targetClassName + "." + targetMethodName + ", " +
                "接口入参: " + stringBuffer.toString() + ", " +
                "接口响应: " + proceed
        );
        return proceed;
    }


}
