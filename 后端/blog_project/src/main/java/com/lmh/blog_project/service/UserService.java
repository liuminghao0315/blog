package com.lmh.blog_project.service;

import com.lmh.blog_project.dataobject.UserDO;
import com.lmh.blog_project.dataobject.UserWithAvatarUrlDO;
import com.lmh.blog_project.dto.UserWithAvatarUrlDTO;
import com.lmh.blog_project.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    // 增
    Integer add(User user);

    // 删
    Integer delete(String id);

    // 查——全部
    List<User> findAll();
    // 查——单个（id）
    User findById(String id);
    // 查——单个（name）
    User findByName(String name);
    //查——单个（token--> id）
    User findUserByToken(String token);
    // 查——单个、avatarUrl(name);
    UserWithAvatarUrlDTO findWithAvatarUrlByName(String name);

    // 改
    Integer update(User user);
    // 改（依据id，改name）
    Integer updateNameById(String id, String name);
}
