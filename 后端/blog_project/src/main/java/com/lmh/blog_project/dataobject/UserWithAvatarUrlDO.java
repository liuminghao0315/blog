package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.dto.UserWithAvatarUrlDTO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class UserWithAvatarUrlDO {
    private String id;
    private String name;
    private String password;
    private String authority;
    private String avatarUrl;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }


    public UserWithAvatarUrlDTO toDTO() {
        UserWithAvatarUrlDTO user = new UserWithAvatarUrlDTO();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
