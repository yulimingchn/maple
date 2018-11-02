package com.dawnyu.maple.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author dawnyu
 */
@Data
public class Category {

    private Long id;

    private String cateName;

    private Integer type;

    private Timestamp createDate;

}
