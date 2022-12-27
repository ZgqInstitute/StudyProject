package com.studyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4J_Simple {
    public static void main(String[] args) {
        //通过Logger工厂对象动态获取我们具体导入的日志实现框架(Log4j、Logback、JUL、slf4j-simple)
        Logger logger = LoggerFactory.getLogger(SLF4J_Simple.class);
        //日志打印方式1
        logger.error("[error  ] 最高级别程序错误");
        logger.warn("[warning] 程序警告不会出现错误");
        logger.info("[info   ] 程序info数据连接，网络连接，IO操作等"); // 默认级别
        logger.debug("[debug  ] 一般在开发中使用，记录程序变量传递信息等等");
        logger.trace("[trace  ] 记录程序所有的流程信息");

        //日志打印方式2
        String name = "祝光泉", age = "23";
        logger.info("info日志记录：我的姓名是:{} 年龄：{}", name, age);

        //日志打印方式3
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            logger.error("程序发生严重错误!", e);
        }
    }
}
