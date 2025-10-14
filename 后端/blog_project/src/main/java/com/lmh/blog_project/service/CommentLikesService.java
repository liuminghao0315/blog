package com.lmh.blog_project.service;

import com.lmh.blog_project.model.CommentLikes;
import org.apache.ibatis.annotations.Param;

public interface CommentLikesService {
    // 增 【当用户点击 点赞 或 倒赞 按钮的时候执行】
    Integer add(CommentLikes commentLikes);

    // 删 【当用户点击 取消点赞 或 取消倒赞 按钮 的时候执行】
    Integer deleteWhenCancel(String commentId, String userId);

    // 删 【当 Root评论被删除 的时候执行】
    Integer deleteWhenDeleteRootComment(String commentId);

    // 删 【当 非Root评论被删除 的时候执行】
    Integer deleteWhenDeleteNonRootComment(String commentId);

    // 删（当 文章被删除的时候执行，依据articleId）
    Integer deleteWhenDeleteArticle(String articleId);

    // 改 【当用户从点赞点成倒赞 或 从倒赞点成点赞 按钮的时候执行】
    Integer update(String commentId, String userId, String incrementNum);
}
