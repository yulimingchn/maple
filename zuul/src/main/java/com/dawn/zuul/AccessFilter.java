package com.dawn.zuul;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 错误拦截器,处理Zuul抛出错误
 * @author dawn
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable e = (Throwable) ctx.remove("throwable");

            if (e != null && e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) e;
                log.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

                HashMap<String, Object> hashMap = new HashMap<>();
                int status = HttpStatus.REQUEST_TIMEOUT.value();
                hashMap.put("result", status);
                hashMap.put("desc", "系统无响应,请稍后再试");
                ctx.setResponseBody(new ObjectMapper().writeValueAsString(hashMap));
                ctx.getResponse().setContentType("application/json");
                ctx.getResponse().setCharacterEncoding("utf-8");
                // Can set any error code as excepted
                ctx.setResponseStatusCode(status);
            }
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

}
