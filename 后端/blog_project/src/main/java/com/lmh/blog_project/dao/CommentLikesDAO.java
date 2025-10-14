package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.CommentLikesDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentLikesDAO {
    // 增 【当用户点击 点赞 或 倒赞 按钮的时候执行】
    Integer add(CommentLikesDO commentLikesDO);

    // 删 【当用户点击 取消点赞 或 取消倒赞 按钮 的时候执行】
    Integer deleteWhenCancel(@Param("commentId") String commentId, @Param("userId") String userId);

    // 删 【当 Root评论被删除 的时候执行】
    Integer deleteWhenDeleteRootComment(@Param("commentId") String commentId);

    // 删 【当 非Root评论被删除 的时候执行】
    Integer deleteWhenDeleteNonRootComment(@Param("commentId") String commentId);

    // 删（当 文章被删除的时候执行，依据articleId）
    Integer deleteWhenDeleteArticle(@Param("articleId") String articleId);

    // 改 【当用户从点赞点成倒赞 或 从倒赞点成点赞 按钮的时候执行】
    Integer update(@Param("commentId") String commentId, @Param("userId") String userId, @Param("incrementNum") String incrementNum);
}
