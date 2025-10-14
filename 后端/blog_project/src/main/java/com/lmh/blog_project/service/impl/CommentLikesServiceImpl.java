package com.lmh.blog_project.service.impl;

import com.lmh.blog_project.dao.CommentLikesDAO;
import com.lmh.blog_project.dataobject.CommentLikesDO;
import com.lmh.blog_project.model.CommentLikes;
import com.lmh.blog_project.service.CommentLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentLikesServiceImpl implements CommentLikesService {
    @Autowired
    private CommentLikesDAO commentLikesDAO;

    @Override
    public Integer add(CommentLikes commentLikes) {
        Integer result = commentLikesDAO.add(new CommentLikesDO(commentLikes));
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenCancel(String commentId, String userId) {
        Integer result = commentLikesDAO.deleteWhenCancel(commentId, userId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenDeleteRootComment(String commentId) {
        Integer result = commentLikesDAO.deleteWhenDeleteRootComment(commentId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenDeleteNonRootComment(String commentId) {
        Integer result = commentLikesDAO.deleteWhenDeleteNonRootComment(commentId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenDeleteArticle(String articleId) {
        Integer result = commentLikesDAO.deleteWhenDeleteArticle(articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer update(String commentId, String userId, String incrementNum) {
        Integer result = commentLikesDAO.update(commentId, userId, incrementNum);
        return Objects.requireNonNullElse(result, 0);
    }
}
