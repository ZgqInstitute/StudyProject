package com.studyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4J_Logback {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SLF4J_Logback.class);
        logger.error("[error  ] 最高级别程序错误" + Thread.currentThread().getName());
        logger.warn("[warning] 程序警告不会出现错误" + Thread.currentThread().getName());
        logger.info("[info   ] 程序info数据连接，网络连接，IO操作等" + Thread.currentThread().getName());
        logger.debug("[debug  ] 一般在开发中使用，记录程序变量传递信息等等" + Thread.currentThread().getName());// 默认级别
        logger.trace("[trace  ] 记录程序所有的流程信息" + Thread.currentThread().getName());

    }
}
