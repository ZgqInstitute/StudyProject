package com.studyLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JCLDemo {
    public static void main(String[] args) {
        //创建日志对象;LogFactory是一个抽象类，LogFactoryImpl才是具体实现
        Log logger = LogFactory.getLog(JCLDemo.class);
        //日志记录输出
        logger.fatal("严重错误，一般造成系统崩溃并终止运行");
        logger.error("错误信息，不会影响系统运行");
        logger.warn("警告信息，可能会发生问题");
        logger.info("运行信息，数据连接，网络连接，IO操作等"); // 默认级别INFO(和JUL默认级别一样)
        logger.debug("调试信息，一般在开发中使用，记录程序变量传递信息等等");
        logger.trace("追踪信息，记录程序所有的流程信息");
    }
}
