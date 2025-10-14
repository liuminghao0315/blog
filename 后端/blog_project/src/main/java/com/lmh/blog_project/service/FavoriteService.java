package com.lmh.blog_project.service;

import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.dto.FavoriteDTO;
import com.lmh.blog_project.model.Favorite;

import java.util.List;

public interface FavoriteService {
    // 增
    Integer add(Favorite favorite);

    // 删 一条（依据userId、articleId）
    Integer delete(String userId, String articleId);

    // 删 【当文章被删除时执行】
    Integer deleteByArticleId(String articleId);

    // 查——若干个article_id （依据user_id）
    List<String> findArticleIdsByUserId(String userId);

    // 查——若干个article对象（Blog对象）依据userId
    Result<Paging<BlogWithUserNameDTO>> findByUserId(String userId, Integer pageNum, Integer pageSize);

    // 查——该文章的收藏数（依据articleId）
    Integer findFavoriteCountByArticleId(String articleId);

    // 查——是否该用户收藏了该文章（依据articleId、userId）
    Integer findIfUserFavorite(String userId, String articleId);


    FavoriteDTO assembleFavoriteDTO(String userId, String articleId);
}
