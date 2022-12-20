package com.studyLog;

import org.apache.log4j.Logger;

public class Log4jDemo {

    public static void main(String[] args) {
        // 开启log4j内置的日志记录
//        LogLog.setInternalDebugging(true);

        Logger logger = Logger.getLogger(Log4jDemo.class);

        logger.fatal("严重错误，一般造成系统崩溃并终止运行");
        logger.error("错误信息，不会影响系统运行");
        logger.warn("警告信息，可能会发生问题");
        logger.info("运行信息，数据连接，网络连接，IO操作等");
        logger.debug("调试信息，一般在开发中使用，记录程序变量传递信息等等");
        logger.trace("追踪信息，记录程序所有的流程信息");


        String s = "A";
        s.toLowerCase();
        s.toUpperCase();
    }
}
