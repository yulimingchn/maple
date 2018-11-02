package com.dawnyu.maple.mapper;

import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

/**
 * @author dawn
 */
@Mapper
public interface ConsumerMapper {

    /**
     * 获取前30天的日期和金额
     * @return
     */
    List<Map> getDataStatistics();


}
