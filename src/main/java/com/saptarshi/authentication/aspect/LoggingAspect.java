package com.saptarshi.authentication.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {
    private final Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.saptarshi.authentication.controller..*(..)))")
    public void beforeEachMethod(JoinPoint joinPoint){
        logger.debug(joinPoint.getSignature().getName());
    }
}
