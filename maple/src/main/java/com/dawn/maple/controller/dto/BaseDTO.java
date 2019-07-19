package com.dawn.maple.controller.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Dawn
 * 2019-04-02
 */
@Data
public class BaseDTO {

    @NotNull(message="appId，应用ID不能为空")
    private Integer appId;

    @Pattern(regexp="^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message="timestamp日期格式错误，合法格式：yyyy-MM-dd HH:mm:ss")
    private String timestamp;

    @NotEmpty(message="sign，签名密钥不能为空")
    private String sign;

}
