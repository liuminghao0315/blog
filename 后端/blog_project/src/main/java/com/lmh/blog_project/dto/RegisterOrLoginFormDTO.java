package com.lmh.blog_project.dto;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.lmh.blog_project.enums.Authority;
import com.lmh.blog_project.enums.Gender;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.model.UserProfile;

import java.io.Serializable;

public class RegisterOrLoginFormDTO implements Serializable {
    private static final long serialVersionUID = 3420074689699767022L;
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User createNewUser(){
        User user = new User();
        user.setId(NanoIdUtils.randomNanoId());
        user.setAuthority(Authority.USER);
        user.setName(userName);
        user.setPassword(password);
        return user;
    }

    public UserProfile createNewUserProfile(String userId){
        UserProfile userProfile = new UserProfile();
        userProfile.setId(NanoIdUtils.randomNanoId());
        userProfile.setUserId(userId);
        return userProfile;
    }

}
