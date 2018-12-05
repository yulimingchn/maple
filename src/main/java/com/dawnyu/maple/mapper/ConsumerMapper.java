package com.dawnyu.maple.mapper;

import com.dawnyu.maple.bean.Consume;
import com.dawnyu.maple.dto.ConsumeStatisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * @author dawn
 */
@Mapper
public interface ConsumerMapper {

    /**
     * 获取前30天的日期和金额
     *
     * @return
     */
    List<ConsumeStatisticsDTO> getDataStatistics();

    /**
     * 新增消费记录
     *
     * @param consume
     * @return
     */
    int addConsume(Consume consume);

    /**
     * 根据关键字分页查询消费记录列表
     *
     * @param start
     * @param count
     * @param keyword
     * @return
     */
    List<Consume> selectListByKeyword(@Param("start") Integer start, @Param("count") Integer count, @Param("keyword") String keyword);

    /**
     * 关键字查询记录总数
     *
     * @param keyword
     * @return
     */
    int selectTotalCount(@Param("keyword") String keyword);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteConsumeByIds(@Param("ids") String[] ids);

    /**
     * 消费记录详情
     * @param id
     * @return
     */
    Consume getConsumeById(@Param("id") Integer id);
}
