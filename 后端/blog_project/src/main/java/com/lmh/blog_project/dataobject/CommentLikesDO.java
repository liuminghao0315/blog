package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.model.CommentLikes;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class CommentLikesDO {
    private String id;
    private String commentId;
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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


    public CommentLikes toModel() {
        CommentLikes commentLikes = new CommentLikes();
        BeanUtils.copyProperties(this, commentLikes);
        return commentLikes;
    }

    public CommentLikesDO() {}

    public CommentLikesDO(CommentLikes commentLikes) {
        BeanUtils.copyProperties(commentLikes, this);
    }

}
