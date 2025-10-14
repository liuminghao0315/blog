package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.model.Comment;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class CommentDO {
    private String id;
    private String articleId;
    private String authorId;
    private String parentId;
    private String rootId;
    private String content;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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


    public Comment toModel(){
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }

    public CommentDO(){}

    public CommentDO(Comment comment){
        BeanUtils.copyProperties(comment, this);
    }
}
