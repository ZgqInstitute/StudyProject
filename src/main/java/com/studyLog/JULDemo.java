package com.studyLog;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JULDemo {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("com.studyLog.JULDemo");

        logger.severe("my log...severe");
        logger.warning("my log...warning");
        logger.info("my log...info");
        logger.config("my log...config");
        logger.fine("my log...fine");
        logger.finer("my log...finer");
        logger.finest("my log...finest");

        logger.log(Level.INFO,"my info log...");

        String name = "祝光泉";
        String age = "24";
        logger.log(Level.INFO, "姓名：{0} 年龄：{1}", new Object[]{name, age});
    }
}
