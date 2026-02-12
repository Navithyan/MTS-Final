package com.fd.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class ProductServiceLoggingAspect {
private static final Logger logger = LoggerFactory.getLogger(ProductServiceLoggingAspect.class);

/*
 * @Before — Advice
 * Defines logic that executes before the target method runs.
 *
 * execution(...) — Pointcut
 * Specifies where the advice should be applied.
 * Targets the getProductsFromDatabase() method in ProductService.
 *
 * JoinPoint
 * Represents the intercepted method execution and provides
 * access to method name, arguments, and target object.
 */
 


@Before("execution(public java.util.List com.fd.service.ProductService.getProductsFromDatabase())")
public void logBeforeFetchingProducts(JoinPoint joinPoint) {
    logger.info("Started method: {}", joinPoint.getSignature().getName());
}


@AfterReturning(
        pointcut = "execution(public * com.fd.service.ProductService.getProductsFromDatabase())",
        returning = "result"
)
public void logAfterFetchingProducts(List<?> result) {
    logger.info("Fetched {} products from database", result.size());
}

}
