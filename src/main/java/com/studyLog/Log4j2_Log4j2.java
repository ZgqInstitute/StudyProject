package com.studyLog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2_Log4j2 {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Log4j2_Log4j2.class);
        logger.fatal("[fatal  ] 最高级别程序错误");
        logger.error("[error  ] 最高级别程序错误");
        logger.warn("[warning] 程序警告不会出现错误");
        logger.info("[info   ] 程序info数据连接，网络连接，IO操作等");
        logger.debug("[debug  ] 一般在开发中使用，记录程序变量传递信息等等");
        logger.trace("[trace  ] 记录程序所有的流程信息");
    }
}
