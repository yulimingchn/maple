package com.dawnyu.maple.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dawn
 */
@Data
public class ConsumeStatisticsDTO {

    private String consumeDate;

    private BigDecimal totalAmount;
}
