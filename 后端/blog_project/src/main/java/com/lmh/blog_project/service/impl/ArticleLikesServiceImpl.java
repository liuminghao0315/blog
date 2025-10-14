package com.lmh.blog_project.service.impl;

import com.lmh.blog_project.dao.ArticleLikesDAO;
import com.lmh.blog_project.dataobject.ArticleLikesDO;
import com.lmh.blog_project.dto.ArticleLikesDTO;
import com.lmh.blog_project.model.ArticleLikes;
import com.lmh.blog_project.service.ArticleLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ArticleLikesServiceImpl implements ArticleLikesService {
    @Autowired
    private ArticleLikesDAO articleLikesDAO;

    @Override
    public Integer add(ArticleLikes articleLikes) {
        Integer result = articleLikesDAO.add(new ArticleLikesDO(articleLikes));
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenCancel(String articleId, String userId) {
        Integer result = articleLikesDAO.deleteWhenCancel(articleId, userId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteWhenDeleteArticle(String articleId) {
        Integer result = articleLikesDAO.deleteWhenDeleteArticle(articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer findByArticleId(String articleId) {
        Integer result = articleLikesDAO.findByArticleId(articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer findMyIncrementInTheArticle(String articleId, String userId) {
        Integer result = articleLikesDAO.findMyIncrementInTheArticle(articleId, userId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer update(String articleId, String userId, String incrementNum) {
        Integer result = articleLikesDAO.update(articleId, userId, incrementNum);
        return Objects.requireNonNullElse(result, 0);
    }


    public ArticleLikesDTO assembleArticleLikesDTO(String articleId, String userId) {
        ArticleLikesDTO articleLikesDTO = new ArticleLikesDTO();
        articleLikesDTO.setLikesNum(findByArticleId(articleId));
        articleLikesDTO.setMyIncrement(findMyIncrementInTheArticle(articleId, userId));
        return articleLikesDTO;
    }
}
