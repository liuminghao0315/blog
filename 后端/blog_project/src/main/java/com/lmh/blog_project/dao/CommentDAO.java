package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.CommentDO;
import com.lmh.blog_project.dataobject.CommentWithUserNameAndAvatarUrlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {
    // 增
    Integer add(CommentDO commentDO);

    // 删（依据 id）
    Integer delete(@Param("id") String id);
    // 删（依据 rootId）
    Integer deleteByRootId(@Param("rootId") String rootId);
    // 删多个（依据 parentId）
    Integer deleteByParentId(@Param("parentId")String parentId);
    // 删（依据 articleId）
    Integer deleteByArticleId(@Param("articleId") String articleId);
    // 删（依据 authorId）
    Integer deleteByAuthorId(@Param("authorId") String authorId);

    // 查——全部
    List<CommentDO> findAll();
    // 查——单个（依据 id）
    CommentDO findById(@Param("id") String id);
    // 查——若干个（依据 articleId）
    List<CommentDO> findByArticleId(@Param("articleId") String articleId);
    // 查——若干个、userName、avatar（依据 articleId）
    List<CommentWithUserNameAndAvatarUrlDO> findWithUserNameAndAvatarUrlDOByArticleId(@Param("articleId") String articleId, @Param("userId") String userId);

    // 改
    Integer update(CommentDO commentDO);
}
