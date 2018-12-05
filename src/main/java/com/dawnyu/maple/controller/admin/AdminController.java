package com.dawnyu.maple.controller.admin;

import com.dawnyu.maple.bean.Article;
import com.dawnyu.maple.bean.Consume;
import com.dawnyu.maple.bean.RespBean;
import com.dawnyu.maple.controller.vo.ConsumeVO;
import com.dawnyu.maple.dto.ConsumeStatisticsDTO;
import com.dawnyu.maple.service.ArticleService;
import com.dawnyu.maple.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
        for (ConsumeStatisticsDTO dto : list) {
            categories.add(dto.getConsumeDate());
            amounts.add(dto.getTotalAmount());
        }
        map.put("categories", categories);
        map.put("amount", amounts);
        return map;
    }

    @RequestMapping(value = "create/consume", method = RequestMethod.POST)
    public RespBean createConsume(ConsumeVO vo) {
        Consume consume = new Consume();
        consume.setConsumeName(vo.getName());
        consume.setConsumeAmount(vo.getAmount());
        consume.setCategoryId(vo.getCategory());
        consume.setConsumeDate(vo.getDate());
        consume.setConsumeDesc(vo.getDesc());
        int id = consumeService.addConsume(consume);
        return new RespBean("success", "" + id);
    }

    @RequestMapping(value = "/consume/all", method = RequestMethod.GET)
    public Map<String, Object> getConsumeByAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "20") Integer count, String keywords) {
        List<Consume> consumes = consumeService.getAllConsumes(page, count, keywords);
        Map<String, Object> map = new HashMap<>(16);
        map.put("consumes", consumes);
        map.put("totalCount", consumeService.totalCount(keywords));
        return map;
    }

    @RequestMapping(value = "consume/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = consumeService.deleteConsumeByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "consume/{id}", method = RequestMethod.GET)
    public Consume getArticleById(@PathVariable Integer id) {
        return consumeService.getConsumeById(id);
    }
}
