package com.dawnyu.maple.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author dawn
 */
@Data
public class Consume {

    private Integer id;

    private String consumeName;

    private BigDecimal consumeAmount;

    private Integer categoryId;

    private String categoryName;

    private String consumeDesc;

    private Date consumeDate;

    private Date createTime;

    private Date updateTime;
}
