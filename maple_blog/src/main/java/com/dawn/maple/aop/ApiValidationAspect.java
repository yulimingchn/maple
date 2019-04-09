package com.dawn.maple.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawn.maple.common.FinalResult;
import com.dawn.maple.common.PageParam;
import com.dawn.maple.controller.dto.BaseDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Dawn
 * 2019-04-02
 */
//@Aspect
//@Component
//@Order(2)
public class ApiValidationAspect {

    protected static Logger logger = LoggerFactory.getLogger(ApiValidationAspect.class);

    private final static String SKIP_VALIDATION_KEY = "skip.validation.key";
    private final static String STR_APP_ID = "appId";
    private final static String STR_TIME_STAMP ="timestamp";

    @Value("${signSecret}")
    private String signSecretConfig;

    /**
     * 切面
     */
    @Pointcut("execution(public * com.dawn.maple.controller.*.*(..))")
    public void controllerPoint() {
    }

    @Around("controllerPoint()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Boolean legal = true;
        String errorMsg = null;
        //密钥配置,请求中的签名参数
        JSONObject signConfigJson = JSON.parseObject(signSecretConfig);
        String secret = null,getSignValue = null,reallySignValue;
        Object[] args = proceedingJoinPoint.getArgs();
        //排序Map
        SortedMap<String, String> sortMap = new TreeMap<>();
        if (args != null) {
            for (Object arg : args) {
                //指定类型的
                if (arg instanceof BaseDTO) {
                    try {
                        //获取所有的POST参数
                        String paramJson = JSON.toJSONString(arg);
                        JSONObject paramMap = JSON.parseObject(paramJson);
                        //逐个读取参数，取到签名参数，并将请求参数放到排序的map中
                        for (String key: paramMap.keySet()){
                            Object valueObj = paramMap.get(key);
                            //TODO 扩展其他结构体类型参数
                            //参数是String或Integer类型的
                            if(valueObj instanceof String || valueObj instanceof Integer){
                                String value = paramMap.getString(key);
                                if (key.equals("sign")) {
                                    getSignValue = value;
                                    logger.info("用户上传的验签MD5 : " + getSignValue);
                                } else {
                                    sortMap.put(key, value);
                                    if(STR_APP_ID.equals(key)){
                                        String appIdValue = paramMap.getString(key);
                                        if(signConfigJson.containsKey(appIdValue)){
                                            secret = signConfigJson.getJSONObject(appIdValue).getString("secret");
                                        }else{
                                            legal = false;
                                            errorMsg = "appId,应用ID非法！";
                                        }
                                    }
                                    if(STR_TIME_STAMP.equals(key)){
                                        //TODO 时间只能与服务器时间相差10分钟以内
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //分页
                }else if(arg instanceof PageParam){
                    PageParam pageParam = (PageParam)arg;
                    sortMap.put("page", String.valueOf(pageParam.getPage()));
                    sortMap.put("size", String.valueOf(pageParam.getSize()));
                }else{
                    //TODO 补充其他类型
                    //URI中的参数，restful类型
                    Object obj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                    if(obj instanceof Map){
                        Map<String,Object> uriParam = (Map)obj;
                        for (String key: uriParam.keySet()){
                            Object uriValue = uriParam.get(key);
                            String uriValueStr = String.valueOf(uriValue);
                            sortMap.put(key, uriValueStr);
                        }
                    }
                }
            }
            if (SKIP_VALIDATION_KEY.equalsIgnoreCase(getSignValue)){
                //为了测试方便,内部跳过验签
                return proceedingJoinPoint.proceed();
            }

            if(getSignValue==null){
                legal = false;
                errorMsg = "参数缺少sign！";
            }

            if(!sortMap.containsKey(STR_APP_ID)){
                legal = false;
                errorMsg = "参数缺少appId！";
            }

            if(!sortMap.containsKey(STR_TIME_STAMP)){
                legal = false;
                errorMsg = "参数缺少timestamp！";
            }

            if(legal){
                //按约定拼装参数
                StringBuilder stringBuilderParamFormat = new StringBuilder();
                stringBuilderParamFormat.append(secret);
                for (Map.Entry<String, String> parm : sortMap.entrySet()) {
                    stringBuilderParamFormat.append(parm.getKey()).append(parm.getValue());
                }
                stringBuilderParamFormat.append(secret);
                String signInput = stringBuilderParamFormat.toString();
                logger.info("验签参数组装字符串 : " + signInput);
                //MD5加密
                reallySignValue = DigestUtils.md5Hex(signInput.getBytes("utf-8"));
                logger.info("实际需求的验签MD5 : " + reallySignValue);
                //签名异常
                if (!reallySignValue.equals(getSignValue)) {
                    legal = false;
                    errorMsg = "sign,签名错误！";
                }
            }
        }
        if(legal){
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        }else{
            //请求参数绑定错误处理
            FinalResult finalResult = FinalResult.create(null,null,0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            finalResult.setParamFail();
            finalResult.setMessage(errorMsg);
            finalResult.setData(new HashMap<>(8));
            return finalResult;
        }
    }
}