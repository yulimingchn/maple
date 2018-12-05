package com.dawnyu.maple.service;

import com.dawnyu.maple.bean.Consume;
import com.dawnyu.maple.dto.ConsumeStatisticsDTO;
import com.dawnyu.maple.mapper.CategoryMapper;
import com.dawnyu.maple.mapper.ConsumerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author dawn
 */
@Service
@Transactional
public class ConsumeService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取最近30天的日期和金额
     *
     * @return
     */
    public List<ConsumeStatisticsDTO> getDataStatistics() {
        return consumerMapper.getDataStatistics();
    }


    /**
     * 新增一条消费记录
     */
    public int addConsume(Consume consume) {
        consume.setCreateTime(new Date());
        consume.setUpdateTime(new Date());
        consume.setCategoryName(categoryMapper.getCategoryDetail(consume.getCategoryId()).getCateName());
        consumerMapper.addConsume(consume);
        return consume.getId();
    }

    public List<Consume> getAllConsumes(Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        return consumerMapper.selectListByKeyword(start,count,keywords);
    }

    public int totalCount(String keywords) {
        return consumerMapper.selectTotalCount(keywords);
    }

    public boolean deleteConsumeByIds(String ids) {
        String[] split = ids.split(",");
        int result = consumerMapper.deleteConsumeByIds(split);
        return result == split.length;
    }

    public Consume getConsumeById(Integer id){

        return consumerMapper.getConsumeById(id);
    }


}
