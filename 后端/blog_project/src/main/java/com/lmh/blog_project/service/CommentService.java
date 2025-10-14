package com.lmh.blog_project.service;

import com.lmh.blog_project.dataobject.CommentWithUserNameAndAvatarUrlDO;
import com.lmh.blog_project.dto.CommentWithUserNameAndAvatarUrlDTO;
import com.lmh.blog_project.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    // 增
    Integer add(Comment comment);

    // 删（依据 id）
    Integer delete(String id);
    // 删（依据 rootId）
    Integer deleteByRootId(String rootId);
    // 删多个（依据 parentId）
    Integer deleteByParentId(String parentId);
    // 删（依据 articleId）
    Integer deleteByArticleId(String articleId);
    // 删（依据 authorId）
    Integer deleteByAuthorId(String authorId);

    // 查——全部
    List<Comment> findAll();
    // 查——单个（依据 id）
    Comment findById(String id);
    // 查——单个（依据 articleId）
    List<Comment> findByArticleId(String articleId);
    // 查——若干个、userName、avatar（依据 articleId）
    List<CommentWithUserNameAndAvatarUrlDTO> findWithUserNameAndAvatarUrlDOByArticleId(String articleId, String userId);

    // 改
    Integer update(Comment comment);

    // 结构化（父子结构List） 评论们（在同一个文章中——articleId相同）
    List<Comment> getStructuredComments(List<Comment> originComments);

    // 结构化（父子结构List） 评论、userName、avatarUrl们（在同一个文章中——articleId相同）
    List<CommentWithUserNameAndAvatarUrlDTO> getStructuredCommentsWithUserNameAndAvatarUrl(List<CommentWithUserNameAndAvatarUrlDTO> originComments);
}
