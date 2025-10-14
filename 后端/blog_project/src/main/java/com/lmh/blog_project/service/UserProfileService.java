package com.lmh.blog_project.service;

import com.lmh.blog_project.dataobject.UserProfileDO;
import com.lmh.blog_project.model.UserProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProfileService {
    // 增
    Integer add(UserProfile userProfile);

    // 删（依据userProfileId）
    Integer delete(String id);

    // 删（依据userId）
    Integer deleteByUserId(String userId);

    // 查——全部
    List<UserProfile> findAll();
    // 查——单个（id）
    UserProfile findById(String id);
    // 查——单个（userId）
    UserProfile findByUserId(String userId);
    // 查——单个（userName）
    UserProfile findByUserName(String userName);

    // 改
    Integer update(UserProfile userProfile);
}
