package com.easypay.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 모든 호출되는 Controller Method 에 대해서 Logging Producer 가 Aspect 로써 Kafka Cluster Pipeline 을 통해
 * STDOUT 으로 인입된 로그에서 어떤 메서드가 호출 되었는지를 확인할 수 있다.
 */
@Aspect
@Component
public class LoggingAspect {
    private final LoggingProducer loggingProducer;

    public LoggingAspect(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;
    }

    @Before("execution(* com.easypay.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        // Produce Access Log
        loggingProducer.sendMessage("logging", "Before executing method: " + methodName);
    }
}
