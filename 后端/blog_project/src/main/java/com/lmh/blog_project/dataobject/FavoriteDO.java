package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.model.Favorite;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class FavoriteDO {
    private String id;
    private String userId;
    private String articleId;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
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


    public FavoriteDO() {}

    public FavoriteDO(Favorite favorite) {
        BeanUtils.copyProperties(favorite, this);
    }

    public Favorite toModel(){
        Favorite favorite = new Favorite();
        BeanUtils.copyProperties(this, favorite);
        return favorite;
    }
}
