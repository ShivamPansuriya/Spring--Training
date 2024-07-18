package com.example.mycart.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest httpServletRequest;

    public LoggingAspect(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Pointcut("execution(* com.example.mycart.service.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* com.example.mycart.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("serviceMethods()")
    public void logMethodEntry(JoinPoint joinPoint)
    {
        var signature = (MethodSignature) joinPoint.getSignature();

        log.debug("Entering method: {} of class: {} with arguments: {}",
                signature.getMethod().getName(),
                signature.getDeclaringTypeName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @Before("controllerMethods()")
    public void logControllerEntry(JoinPoint joinPoint)
    {
        var signature = (MethodSignature) joinPoint.getSignature();

        log.debug("Entering request uri: {} method: {} of class: {} with arguments: {}",
                httpServletRequest.getRequestURI(),
                signature.getMethod().getName(),
                signature.getDeclaringTypeName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "serviceMethods() && controllerMethods()", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Exception ex)
    {
        var signature = (MethodSignature) joinPoint.getSignature();

        log.error("Exception in method: {} of class: {} with arguments: {}. Exception: {}",
                signature.getMethod().getName(),
                signature.getDeclaringTypeName(),
                Arrays.toString(joinPoint.getArgs()),
                ex.getMessage());
    }

    @Around("serviceMethods()")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        var signature = (MethodSignature) joinPoint.getSignature();

        var startTime = System.currentTimeMillis();

        var result = joinPoint.proceed();

        var elapsedTime = System.currentTimeMillis() - startTime;

        log.debug("Exiting method: {} with result. Execution time: {} ms",
                signature.getMethod().getName(),
                elapsedTime);

        return result;
    }
}
