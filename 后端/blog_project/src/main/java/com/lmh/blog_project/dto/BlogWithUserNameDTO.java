package com.lmh.blog_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lmh.blog_project.dataobject.BlogDO;
import com.lmh.blog_project.dataobject.UserDO;
import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.model.BlogWithUserName;
import com.lmh.blog_project.model.User;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BlogWithUserNameDTO implements Serializable {
    private static final long serialVersionUID = -773353138382615086L;
    private String id;
    private String title;
    private String authorName;
    private String content;
    private String summary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtCreated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
    private Integer likeCount;
    private Integer favoriteCount;
    private Integer commentCount;

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

    public BlogWithUserNameDTO() {}

    public BlogWithUserNameDTO(Blog blog, User user){
        BeanUtils.copyProperties(new BlogDO(blog), this);
        this.authorName = new UserDO(user).getName();
    }

    public Map<String,Object> toDOAndUserName(){
        Map<String,Object> model = new HashMap<>();
        BlogDO blogDO = new BlogDO();
        BeanUtils.copyProperties(this, blogDO);
        blogDO.setAuthorId("-1");
        model.put("blogDO", blogDO);
        model.put("userName",this.authorName);
        return model;
    }



    public BlogWithUserNameDTO(BlogWithUserName blogWithUserName){
        BeanUtils.copyProperties(blogWithUserName, this);
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
