package com.dawn.maple.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author dawn
 * 消费记录视图对象
 */
@Data
public class ConsumeVO {

    private Integer id;

    private String name;

    private BigDecimal amount;

    private Integer category;

    private String categoryName;

    private String desc;

    private Date date;

    private Date createTime;

    private Date updateTime;


}
