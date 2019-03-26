package com.dawnyu.maple.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author dawnyu
 */
@Data
public class User  {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private boolean enabled;

    private List<Role> roles;

    private String email;

    private String userface;

    private Timestamp regTime;

    private Date createTime;

    private Date updateTime;


}
