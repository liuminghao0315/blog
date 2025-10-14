package com.lmh.blog_project.api;

import com.lmh.blog_project.dto.ArticleLikesDTO;
import com.lmh.blog_project.model.ArticleLikes;
import com.lmh.blog_project.service.ArticleLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ArticleLikesApi {
    @Autowired
    private ArticleLikesService articleLikesService;

    @RequestMapping("/db/articleLikes/add")
    public Integer add(@RequestBody ArticleLikes articleLikes) {
        return articleLikesService.add(articleLikes);
    }

    @RequestMapping("/db/articleLikes/deleteWhenCancel")
    public Integer deleteWhenCancel(String articleId, String userId) {
        return articleLikesService.deleteWhenCancel(articleId, userId);
    }

    @RequestMapping("/db/articleLikes/deleteWhenDeleteArticle")
    public Integer deleteWhenDeleteArticle(String articleId) {
        return articleLikesService.deleteWhenDeleteArticle(articleId);
    }

    @RequestMapping("/db/articleLikes/getDTO")
    public ArticleLikesDTO getDTO(String articleId, String userId) {
        return articleLikesService.assembleArticleLikesDTO(articleId, userId);
    }

    @RequestMapping("/db/articleLikes/update")
    public Integer update(String articleId, String userId, String incrementNum) {
        return articleLikesService.update(articleId, userId, incrementNum);
    }
}
