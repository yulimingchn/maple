package com.dawnyu.bean;

import lombok.Data;

/**
 * @author dawnyu
 */
@Data
public class Role {
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {

        this.id = id;
        this.name = name;
    }
}
