package com.micezhao.data.mongodb.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAop {
	
	private final static Logger logger = LoggerFactory.getLogger(LoggerAop.class);
	
	@Before(value="@annotation(LoggerAspect)")
    public void dofore(JoinPoint joinPoint) {
		logger.info("log before method");
    }
	
}
