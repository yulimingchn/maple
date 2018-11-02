package com.dawnyu.maple.service;

import com.dawnyu.maple.mapper.ConsumerMapper;
import com.dawnyu.maple.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author dawn
 */
@Service
@Transactional
public class ConsumeService {

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 获取最近30天的日期和金额
     * @return
     */
    public List<Map> getDataStatistics() {
        return consumerMapper.getDataStatistics();
    }




}
