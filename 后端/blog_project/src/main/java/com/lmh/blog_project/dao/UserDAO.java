package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.UserDO;
import com.lmh.blog_project.dataobject.UserWithAvatarUrlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO {
    // 增
    Integer add(UserDO userDO);

    // 删
    Integer delete(@Param("id") String id);

    // 查——全部
    List<UserDO> findAll();
    // 查——单个（id）
    UserDO findById(@Param("id") String id);
    // 查——单个（name）
    UserDO findByName(@Param("name") String name);
    // 查——单个、avatarUrl(name);
    UserWithAvatarUrlDO findWithAvatarUrlByName(@Param("name") String name);

    // 改
    Integer update(UserDO userDO);
    // 改（依据id，改name）
    Integer updateNameById(@Param("id") String id, @Param("name") String name);
}
