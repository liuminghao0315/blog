package com.lmh.blog_project.dataobject;

import com.lmh.blog_project.enums.Gender;
import com.lmh.blog_project.model.UserProfile;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserProfileDO {
    private String id;
    private String userId;
    private String avatarUrl;
    private String gender = "Blank";
    private String signature;
    private LocalDate birthday;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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


    public UserProfile toModel(){
        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(this, userProfile);
        userProfile.setGender(Gender.valueOf(gender));
        return userProfile;
    }

    public UserProfileDO(){}

    public UserProfileDO(UserProfile userProfile){
        BeanUtils.copyProperties(userProfile, this);
            this.gender = userProfile.getGender().toString();
    }
}
