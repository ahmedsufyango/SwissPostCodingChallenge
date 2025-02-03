package com.swisspost.cryptowallet.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log method entry
    @Before("execution(* com.swisspost.cryptowallet..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.swisspost.cryptowallet..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Completed: " + joinPoint.getSignature().getName());
    }

}
