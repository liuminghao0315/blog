package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.dto.CommentLikesDTO;
import com.lmh.blog_project.dto.CommentWithUserNameAndAvatarUrlDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class CommentWithUserNameAndAvatarUrlDO {
    private String id;
    private String articleId;
    private String authorId;
    private String parentId;
    private String rootId;
    private String content;
    private CommentLikesDTO commentLikesDTO;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private String userName;
    private String avatarUrl;

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

    public CommentLikesDTO getCommentLikesDTO() {
        return commentLikesDTO;
    }

    public void setCommentLikesDTO(CommentLikesDTO commentLikesDTO) {
        this.commentLikesDTO = commentLikesDTO;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public CommentWithUserNameAndAvatarUrlDTO toDTO(){
        CommentWithUserNameAndAvatarUrlDTO commentWithUserNameAndAvatarUrlDTO = new CommentWithUserNameAndAvatarUrlDTO();
        BeanUtils.copyProperties(this, commentWithUserNameAndAvatarUrlDTO);
        return commentWithUserNameAndAvatarUrlDTO;
    }
}
