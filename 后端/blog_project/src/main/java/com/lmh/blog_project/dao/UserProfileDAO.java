package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.UserProfileDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserProfileDAO {
    // 增
    Integer add(UserProfileDO userProfileDO);

    // 删（依据userProfileId）
    Integer delete(@Param("id") String id);

    // 删（依据userId）
    Integer deleteByUserId(@Param("userId") String userId);

    // 查——全部
    List<UserProfileDO> findAll();
    // 查——单个（id）
    UserProfileDO findById(@Param("id") String id);
    // 查——单个（userId）
    UserProfileDO findByUserId(@Param("userId") String userId);

    // 改
    Integer update(UserProfileDO userProfileDO);
}
