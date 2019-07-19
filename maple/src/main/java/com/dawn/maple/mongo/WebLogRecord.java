package com.dawn.maple.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author dawn
 * web请求日志记录
 */
@Document
@Data
public class WebLogRecord {
    @Indexed
    private Integer requestHash;
    private String agent;
    private String ip;
    private String token;
    @Indexed
    private Integer userId;
    private String userName;
    private String userPhone;
    @Indexed
    private String method;
    private String methodDesc;
    @Indexed
    private Date timestamp = new Date();
    private Object[] params;
    private Object ex;
    private Object result;
    private Long spendTime;
}



