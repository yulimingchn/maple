package com.dawn.zuul;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

/**
 * 捕捉Spring异常信息,返回友好Json体
 * 
 * @author admin
 *
 */
@ControllerAdvice()
@Order(0)
public class CommonExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(WebRequest request, Exception ex) {
        return handleExceptionInternal(ex, "业务异常,请联系服务方", null, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, request);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerRuntimeException(WebRequest request, Exception ex) {
        return handleExceptionInternal(ex, "业务异常,请联系服务方", null, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, request);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerMultipartException(WebRequest request, Exception ex) {
        return handleExceptionInternal(ex, "文件大小超过限制", null, HttpStatus.PAYLOAD_TOO_LARGE, request);
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    ResponseEntity<?> handleRequestTooBigException(WebRequest request, Exception ex) {
        return handleExceptionInternal(ex, "文件大小超过限制", null, HttpStatus.PAYLOAD_TOO_LARGE, request);
    }
    
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException
                || ex instanceof HttpMessageNotReadableException || ex instanceof MethodArgumentNotValidException) {
            log.error(ex.getMessage());
        } else if (ex instanceof ResponseStatusException) {
            log.error(ex.getMessage());
            body = ((ResponseStatusException) ex).getReason();
            status = HttpStatus.NOT_FOUND;
        } else {
            log.error(ex.getMessage(), ex);
        }
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("desc", body == null ? ex.getMessage() : body);
        errorAttributes.put("result", status.value());
        return new ResponseEntity<Object>(errorAttributes, status);
    }

}