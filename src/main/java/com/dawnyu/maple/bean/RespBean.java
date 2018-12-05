package com.dawnyu.maple.bean;

import lombok.Data;

/**
 * @author dawnyu
 */
@Data
public class RespBean {

    private String status;

    private String msg;

    public RespBean(){}

    public RespBean(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
