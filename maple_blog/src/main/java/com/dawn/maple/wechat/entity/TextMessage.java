package com.dawn.maple.wechat.entity;

import lombok.Data;

/**
 * 文本消息类
 * @author dawn
 */
@Data
public class TextMessage extends BaseMessage {

    private String Content;

    private String MsgId;

}
