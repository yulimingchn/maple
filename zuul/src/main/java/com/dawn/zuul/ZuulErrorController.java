package com.dawn.zuul;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * 捕捉没有被ErrorFilter拦截的错误信息(如Undertow异常)
 * @author dawn
 */
@RestController
public class ZuulErrorController extends AbstractErrorController {

    @Value("${error.path:/error}")
    private String errorPath;

    public ZuulErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
    public ResponseEntity error(HttpServletRequest request) throws Throwable {
        Map<String, Object> body = getErrorAttributes(request, true);
        HttpStatus status = getStatus(request);
        if (status.equals(HttpStatus.NOT_FOUND)) {
            throw new ResponseStatusException(status, "资源不存在");
        }
        try {
            String object = (String) body.get("message");
            String[] split = object.split(":");
            Throwable forName = (Throwable) Class.forName(split[0]).newInstance();
            throw forName;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<>(body, status);
    }
}