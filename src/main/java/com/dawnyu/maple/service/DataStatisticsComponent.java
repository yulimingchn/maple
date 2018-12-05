package com.dawnyu.maple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务统计每个用户的文章访问量
 *  @author dawnyu
 */
@Component
public class DataStatisticsComponent {

    private final  static Logger LOGGER = LoggerFactory.getLogger(DataStatisticsComponent.class);

    @Autowired
    ArticleService articleService;

    /**每天夜里11点半执行一次，统计PV*/
    @Scheduled(cron = "0 30 23  * * ? ")
    public void pvStatisticsPerDay() {
        LOGGER.info("统计pv的定时任务开启。。。。。");
        articleService.pvStatisticsPerDay();
    }
}
