package com.dawn.maple.aop;

import com.alibaba.fastjson.JSON;
import com.dawn.maple.controller.dto.BaseDTO;
import com.dawn.maple.mongo.WebLogRecord;
import com.dawn.maple.utils.DateTypeHandler;
import com.dawn.maple.utils.DateUtils;
import com.dawn.maple.utils.MapleUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dawn
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    protected static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    public static Set<String> collectionNameSet = new HashSet<>();

    ThreadLocal<WebLogRecord> webLogRecordThreadLocal = new ThreadLocal<>();

    @Value("${spring.application.name}")
    private String applicationName;

    /*@Autowired
    private MongoTemplate mongoTemplate;*/

    /**
     * 切面
     */
    @Pointcut("execution(public * com.dawn.maple.controller.*.*(..))")
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
        WebLogRecord webLogRecord = new WebLogRecord();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + MapleUtil.getIpAddr(request));
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        webLogRecord.setAgent(request.getHeader("User-Agent"));
        webLogRecord.setIp(MapleUtil.getIpAddr(request));
        webLogRecord.setUserId(100000);
        webLogRecord.setUserName("dawn");
        webLogRecord.setUserPhone("15632565623");
        webLogRecord.setMethod(request.getMethod());
        webLogRecord.setRequestHash(request.hashCode());
        webLogRecord.setTimestamp(new Date());
        webLogRecord.setParams(args);
        webLogRecordThreadLocal.set(webLogRecord);
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
        long spendTime = System.currentTimeMillis() - startTime.get();
        logger.info("SPEND TIME : " + spendTime + " MS");
        WebLogRecord webLogRecord = webLogRecordThreadLocal.get();
        webLogRecord.setResult(ret);
        webLogRecord.setSpendTime(spendTime);
        String collectionName = applicationName + "_webLogRecord_" + DateUtils.formatDate(new Date(), "YYYY-MM");
        //mongoTemplate.save(webLogRecord,collectionName);
        createMongoIndex(collectionName);
    }

    private void createMongoIndex(String collectionName) {
        if (!collectionNameSet.contains(collectionName)) {
            //mongoTemplate.indexOps(collectionName).ensureIndex(new Index().on("requestHash", Sort.Direction.ASC));
           // mongoTemplate.indexOps(collectionName).ensureIndex(new Index().on("userId", Sort.Direction.ASC));
            //mongoTemplate.indexOps(collectionName).ensureIndex(new Index().on("method", Sort.Direction.ASC));
            //mongoTemplate.indexOps(collectionName).ensureIndex(new Index().on("timestamp", Sort.Direction.DESC));
            collectionNameSet.add(collectionName);
        }
    }
}