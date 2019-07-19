package com.dawn.maple.common;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Dawn
 * 分页参数
 */
@Data
public class PageParam {

    @NotNull(message = "分页参数page不能为空")
    private Integer page;

    @NotNull(message = "分页参数size不能为空")
    private Integer size;

}
