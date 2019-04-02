package com.dawn.maple.aop;

import com.alibaba.fastjson.JSON;
import com.dawn.maple.controller.dto.BaseDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dawn
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    protected static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切面
     */
    @Pointcut("execution(public * com.dawn.maple.controller..*(..))")
    public void facadeImplPoint(){}

    /**
     * 拦截 com.dawn.maple.controller包下面的所有类中，有@RequestMapping注解的方法。和带有getMapping和PostMapping注解的方法
     */
    @Pointcut("execution(* com.dawn.maple.controller..*(..))&& @annotation(org.springframework.web.bind.annotation.RequestMapping)"
            +"||@annotation(org.springframework.web.bind.annotation.GetMapping)"
            +"||@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void controllerMethodPointcut(){}

    @Before("facadeImplPoint()")
    public void doBefore(JoinPoint joinPoint){
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if(args!=null){
            int num = 0;
            for (Object arg : args){
                num++;
                if(arg instanceof String){
                    logger.info("ARGS"+num+" : "+arg);
                }
                else if(arg instanceof BaseDTO){
                    try {
                        logger.info("ARGS"+num+" : "+ JSON.toJSONString(arg));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    logger.info("ARGS"+num+" : "+arg);
                }
            }
        }
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", pointcut = "facadeImplPoint()")
    public void doAfterReturning(Object ret){
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + " MS");
    }
}