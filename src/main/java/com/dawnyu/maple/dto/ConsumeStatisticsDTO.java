package com.dawnyu.maple.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dawn
 */
@Data
public class ConsumeStatisticsDTO {

    /**
     * 消费日期
     */
    private String consumeDate;

    /**
     * 消费金额
     */
    private BigDecimal totalAmount;

}
