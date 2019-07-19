package com.dawn.maple.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author dawnyu
 */
@Data
public class Role {

    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;

    public Role() {
    }

    public Role(Long id, String name) {

        this.id = id;
        this.name = name;
    }
}
