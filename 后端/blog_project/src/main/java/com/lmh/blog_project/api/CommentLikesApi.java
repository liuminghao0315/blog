package com.lmh.blog_project.api;

import com.lmh.blog_project.model.CommentLikes;
import com.lmh.blog_project.service.CommentLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class CommentLikesApi {
    @Autowired
    private CommentLikesService commentLikesService;

    @RequestMapping("/db/commentLikes/add")
    public Integer add(@RequestBody CommentLikes commentLikes) {
        return commentLikesService.add(commentLikes);
    }

    @RequestMapping("/db/commentLikes/deleteWhenCancel")
    public Integer deleteWhenCancel(String commentId, String userId) {
        return commentLikesService.deleteWhenCancel(commentId, userId);
    }

    @RequestMapping("/db/commentLikes/deleteWhenDeleteRootComment")
    public Integer deleteWhenDeleteRootComment(String commentId) {
        return commentLikesService.deleteWhenDeleteRootComment(commentId);
    }

    @RequestMapping("/db/commentLikes/deleteWhenDeleteNonRootComment")
    public Integer deleteWhenDeleteNonRootComment(String commentId) {
        return commentLikesService.deleteWhenDeleteNonRootComment(commentId);
    }

    @RequestMapping("/db/commentLikes/deleteWhenDeleteArticle")
    public Integer deleteWhenDeleteArticle(String articleId) {
        return commentLikesService.deleteWhenDeleteArticle(articleId);
    }

    @RequestMapping("/db/commentLikes/update")
    public Integer update(String commentId, String userId, String incrementNum) {
        return commentLikesService.update(commentId, userId, incrementNum);
    }
}
