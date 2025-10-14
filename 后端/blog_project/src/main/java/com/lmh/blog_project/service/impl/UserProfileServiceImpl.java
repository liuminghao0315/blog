package com.lmh.blog_project.service.impl;

import com.lmh.blog_project.dao.UserDAO;
import com.lmh.blog_project.dao.UserProfileDAO;
import com.lmh.blog_project.dataobject.UserProfileDO;
import com.lmh.blog_project.model.UserProfile;
import com.lmh.blog_project.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileDAO userProfileDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Integer add(UserProfile userProfile) {
        return userProfileDAO.add(new UserProfileDO(userProfile));
    }

    @Override
    public Integer delete(String id) {
        return userProfileDAO.delete(id);
    }

    @Override
    public Integer deleteByUserId(String userId) {
        return userProfileDAO.deleteByUserId(userId);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileDAO.findAll().stream().map(UserProfileDO::toModel).collect(Collectors.toList());
    }

    @Override
    public UserProfile findById(String id) {
        return userProfileDAO.findById(id).toModel();
    }

    @Override
    public UserProfile findByUserId(String userId) {
        return userProfileDAO.findByUserId(userId).toModel();
    }

    @Override
    public UserProfile findByUserName(String userName) {
        if(userDAO.findByName(userName)==null){
            return null;
        }
        return userProfileDAO.findByUserId(userDAO.findByName(userName).getId()).toModel();
    }

    @Override
    public Integer update(UserProfile userProfile) {
        return userProfileDAO.update(new UserProfileDO(userProfile));
    }
}
