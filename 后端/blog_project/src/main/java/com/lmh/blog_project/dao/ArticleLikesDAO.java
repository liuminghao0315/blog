package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.ArticleLikesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleLikesDAO {
    // 增 【当用户点击 点赞 或 倒赞 按钮的时候执行】
    Integer add(ArticleLikesDO articleLikesDO);

    // 删 【当用户点击 取消点赞 或 取消倒赞 按钮 的时候执行】
    Integer deleteWhenCancel(@Param("articleId") String articleId, @Param("userId") String userId);

    // 删 【当 文章被删除 的时候执行】
    Integer deleteWhenDeleteArticle(@Param("articleId") String articleId);

    // 查 点赞数量（依据articleId）
    Integer findByArticleId(@Param("articleId") String articleId);

    // 查 该用户在当前文章的点赞状态（依据articleId、userId）
    Integer findMyIncrementInTheArticle(@Param("articleId") String articleId,@Param("userId") String userId);

    // 改 【当用户从点赞点成倒赞 或 从倒赞点成点赞 按钮的时候执行】
    Integer update(@Param("articleId") String articleId, @Param("userId") String userId, @Param("incrementNum") String incrementNum);
}
