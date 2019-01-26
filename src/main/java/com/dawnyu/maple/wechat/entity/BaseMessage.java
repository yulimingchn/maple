package com.dawnyu.maple.wechat.entity;

import lombok.Data;

/**
 * 消息体基础类
 */
@Data
public class BaseMessage {

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private String MsgType;


}
