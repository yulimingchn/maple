package com.dawnyu.maple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.dawnyu.maple.bean.Article;

import java.util.List;

/**
 * @author dawnyu
 */
@Mapper
public interface ArticleMapper {

    /**
     * 新增文章
     * @param article
     * @return
     */
    int addNewArticle(Article article);

    /**
     * 修改文章
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 根据状态获取文章列表
     * @param state
     * @param start
     * @param count
     * @param uid
     * @param keywords
     * @return
     */
    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid,@Param("keywords") String keywords);

//    List<Article> getArticleByStateByAdmin(@Param("start") int start, @Param("count") Integer count, @Param("keywords") String keywords);

    /**
     * 根据状态获取文章总数
     * @param state
     * @param uid
     * @param keywords
     * @return
     */
    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    /**
     * 修改文章状态
     * @param aids
     * @param state
     * @return
     */
    int updateArticleState(@Param("aids") Long [] aids, @Param("state") Integer state);

    /**
     * 根据Id删除文章
     * @param aids
     * @return
     */
    int deleteArticleById(@Param("aids") Long[] aids);

    /**
     * 获取文章详情
     * @param aid
     * @return
     */
    Article getArticleById(Long aid);

    /**
     * 文章浏览次数增加
     * @param aid
     */
    void pvIncrement(Long aid);

    /**INSERT INTO pv(countDate,pv,uid) SELECT NOW(),SUM(pageView),uid FROM article GROUP BY uid*/
    void pvStatisticsPerDay();

    /**
     * 获取最近七天的日期
     * @param uid
     * @return
     */
    List<String> getCategories(Long uid);

    /**
     * 获取最近七天的数据
     * @param uid
     * @return
     */
    List<Integer> getDataStatistics(Long uid);
}
