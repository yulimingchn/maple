package com.dawnyu.maple.controller.admin;

import com.dawnyu.maple.dto.ConsumeStatisticsDTO;
import com.dawnyu.maple.service.ArticleService;
import com.dawnyu.maple.bean.Article;
import com.dawnyu.maple.bean.RespBean;
import com.dawnyu.maple.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dawnyu
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ArticleService articleService;

    @Autowired
    private ConsumeService consumeService;

    @RequestMapping(value = "/article/all", method = RequestMethod.GET)
    public Map<String, Object> getArticleByStateByAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        List<Article> articles = articleService.getArticleByState(-2, page, count, keywords);
        Map<String, Object> map = new HashMap<>(16);
        map.put("articles", articles);
        map.put("totalCount", articleService.getArticleCountByState(1, null, keywords));
        return map;
    }

    @RequestMapping(value = "/article/dustbin", method = RequestMethod.PUT)
    public RespBean updateArticleState(Long[] aids, Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "/amountStatistics", method = RequestMethod.GET)
    public Map<String, Object> getConsumerStatistics() {
        Map<String, Object> map = new HashMap<>(16);
        List<ConsumeStatisticsDTO> list = consumeService.getDataStatistics();
        List<String> categories = new ArrayList<>();
        List<BigDecimal> amounts = new ArrayList<>();
        for (ConsumeStatisticsDTO dto : list){
            categories.add(dto.getConsumeDate());
            amounts.add(dto.getTotalAmount());
        }
        map.put("categories", categories);
        map.put("amount", amounts);
        return map;
    }
}
