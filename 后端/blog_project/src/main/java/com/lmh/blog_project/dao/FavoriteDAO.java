package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.FavoriteDO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteDAO {
    // 增
    Integer add(FavoriteDO favoriteDO);

    // 删 一条（依据userId、articleId）
    Integer delete(@Param("userId")String userId, @Param("articleId")String articleId);

    // 删 【当文章被删除时执行】
    Integer deleteByArticleId(@Param("articleId")String articleId);

    // 查——若干个article_id （依据user_id）
    List<String> findArticleIdsByUserId(@Param("userId")String userId);

    // 查——若干个article对象（Blog对象）依据userId
    List<BlogWithUserNameDTO> findByUserId(@Param("userId")String userId);

    // 查——该文章的收藏数（依据articleId）
    Integer findFavoriteCountByArticleId(@Param("articleId")String articleId);

    // 查——是否该用户收藏了该文章（依据articleId、userId）
    Integer findIfUserFavorite(@Param("userId")String userId, @Param("articleId")String articleId);
}
