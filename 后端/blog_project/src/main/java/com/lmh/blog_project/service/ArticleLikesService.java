package com.lmh.blog_project.service;

import com.lmh.blog_project.dto.ArticleLikesDTO;
import com.lmh.blog_project.model.ArticleLikes;

public interface ArticleLikesService {
    // 增 【当用户点击 点赞 或 倒赞 按钮的时候执行】
    Integer add(ArticleLikes articleLikes);

    // 删 【当用户点击 取消点赞 或 取消倒赞 按钮 的时候执行】
    Integer deleteWhenCancel(String articleId, String userId);

    // 删 【当 文章被删除 的时候执行】
    Integer deleteWhenDeleteArticle(String articleId);

    // 查 点赞数量（依据ArticleId）
    Integer findByArticleId(String articleId);

    // 查 该用户在当前文章的点赞状态（依据ArticleId、userId）
    Integer findMyIncrementInTheArticle(String articleId, String userId);

    // 改 【当用户从点赞点成倒赞 或 从倒赞点成点赞 按钮的时候执行】
    Integer update(String articleId, String userId, String incrementNum);


    ArticleLikesDTO assembleArticleLikesDTO(String articleId, String userId);
}
