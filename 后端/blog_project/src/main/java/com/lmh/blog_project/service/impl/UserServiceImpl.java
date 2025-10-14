package com.lmh.blog_project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.dao.UserDAO;
import com.lmh.blog_project.dataobject.UserDO;
import com.lmh.blog_project.dataobject.UserWithAvatarUrlDO;
import com.lmh.blog_project.dto.UserWithAvatarUrlDTO;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.service.UserService;
import com.lmh.blog_project.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Integer add(User user) {
        if(userDAO.findByName(user.getName())!=null){
            return 0;
        }
        return userDAO.add(new UserDO(user));
    }

    @Override
    public Integer delete(String id) {
        return userDAO.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll().stream().map(UserDO::toModel).collect(Collectors.toList());
    }

    @Override
    public User findById(String id) {
        return userDAO.findById(id).toModel();
    }



    @Override
    public User findByName(String name) {
        UserDO user = userDAO.findByName(name);
        return user==null?null:user.toModel();
    }

    @Override
    public User findUserByToken(String token) {
        Map<String,String> map = new HashMap<>();
        if(jwtUtil.validateToken(token,map)){
            System.out.println(map.get("userId"));
            return findById(map.get("userId"));
        }else{
            return null;
        }
    }

    @Override
    public UserWithAvatarUrlDTO findWithAvatarUrlByName(String name) {
        UserWithAvatarUrlDO user = userDAO.findWithAvatarUrlByName(name);
        if(user==null) return null;
        return user.toDTO();
    }

    @Override
    public Integer update(User user) {
        return userDAO.update(new UserDO(user));
    }

    @Override
    public Integer updateNameById(String id, String name) {
        return userDAO.updateNameById(id,name);
    }
}
