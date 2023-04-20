package com.example.jiuYe2.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class InformAspect {

//    @Before("execution(* com.example.jiuYe2.controller.*.*(..))")
//    public void beforeMethod(JoinPoint joinPoint){
//        StringBuilder sb = new StringBuilder("参数为：");
//        for (Object arg : joinPoint.getArgs()) {
//            if (arg != null) {
//                sb.append(arg + " | ");
//            }
//        }
//        log.info("方法运行前……" + sb);
//    }
//
//    @After("execution(* com.example.jiuYe2.controller.*.*(..))")
//    public void afterMethod(){
//        log.info("方法运行后……");
//    }

}
