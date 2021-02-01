package com;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootTest {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testLog(){
		logger.trace("这是trace级别");
		logger.debug("这是debug级别");
		logger.info("这是info级别");
		logger.warn("这是warn级别");
		logger.error("这是error级别");
	}

}
