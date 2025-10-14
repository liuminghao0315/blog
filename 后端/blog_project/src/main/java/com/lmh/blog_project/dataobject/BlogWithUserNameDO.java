package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.model.BlogWithUserName;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class BlogWithUserNameDO {
    private String id;
    private String title;
    private String authorName;
    private String content;
    private String summary;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private Integer likeCount;
    private Integer favoriteCount;
    private Integer commentCount;
    private Integer rowNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    @Override
    public String toString() {
        return "BlogWithUserNameDO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", likeCount=" + likeCount +
                ", favoriteCount=" + favoriteCount +
                ", commentCount=" + commentCount +
                ", rowNum=" + rowNum +
                '}';
    }

    public BlogWithUserName toModel() {
        BlogWithUserName model = new BlogWithUserName();
        BeanUtils.copyProperties(this, model);
        return model;
    }

    public BlogWithUserNameDO(){}

    public BlogWithUserNameDO(BlogWithUserName blogWithUserName) {
        BeanUtils.copyProperties(blogWithUserName, this);
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }
}
