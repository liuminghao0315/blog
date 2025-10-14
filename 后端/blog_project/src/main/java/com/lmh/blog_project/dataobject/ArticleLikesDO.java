package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.model.ArticleLikes;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class ArticleLikesDO {
    private String id;
    private String articleId;
    private String userId;
    private Integer incrementNum;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIncrementNum() {
        return incrementNum;
    }

    public void setIncrementNum(Integer incrementNum) {
        this.incrementNum = incrementNum;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }


    public ArticleLikes toModel() {
        ArticleLikes articleLikes = new ArticleLikes();
        BeanUtils.copyProperties(this, articleLikes);
        return articleLikes;
    }

    public ArticleLikesDO() {}

    public ArticleLikesDO(ArticleLikes articleLikes) {
        BeanUtils.copyProperties(articleLikes, this);
    }
}
