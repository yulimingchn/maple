package com.dawn.maple.common;

import lombok.Data;

import java.util.List;

/**
 * @author Dawn
 * 2019-04-02
 * 分页数据响应
 */
@Data
public class PageResponse<T> {

    /**
     * 数据总计
     */
    private int total;

    /**
     * 当前页数
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 数据长度
     */
    private int size;

    /**
     * 总计页数
     */
    private int pages;

    /**
     * 数据集合
     */
    private List<T> list;

}
