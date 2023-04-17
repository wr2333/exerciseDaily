package com.example.jiuYe2.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class InformAspect {

    @Before("execution(* com.example.jiuYe2.controller.*.*(..))")
    public void beforeMethod(){
        log.info("方法运行前……");
    }

    @After("execution(* com.example.jiuYe2.controller.*.*(..))")
    public void afterMethod(){
        log.info("方法运行后……");
    }
}
