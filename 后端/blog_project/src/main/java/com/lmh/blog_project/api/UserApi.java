package com.lmh.blog_project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.dto.RegisterOrLoginFormDTO;
import com.lmh.blog_project.dto.UserDTO;
import com.lmh.blog_project.dto.UserWithAvatarUrlDTO;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.model.UserProfile;
import com.lmh.blog_project.service.UserProfileService;
import com.lmh.blog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin("*")
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping("/db/user/add")
    public String addUser(@RequestBody RegisterOrLoginFormDTO registerOrLoginFormDTO) {
        User ifUser = userService.findByName(registerOrLoginFormDTO.getUserName());
        if(ifUser != null) {
            return "0";
        }
        User newUser = registerOrLoginFormDTO.createNewUser();
        UserProfile newUserProfile = registerOrLoginFormDTO.createNewUserProfile(newUser.getId());
        try {
            new ObjectMapper().writeValueAsString(newUserProfile);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userService.add(newUser) + "" + userProfileService.add(newUserProfile);
    }

    @RequestMapping("/db/user/delete")
    public Integer deleteUser(String id) {
        return userService.delete(id) + userProfileService.deleteByUserId(id);
    }

    @RequestMapping("/db/user/findall")
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/db/user/findbyid")
    public User findUserById(String id) {
        return userService.findById(id);
    }

    @RequestMapping("/db/user/findWithAvatarUrlByName")
    public UserWithAvatarUrlDTO findWithAvatarUrlByName(String name) {
        return userService.findWithAvatarUrlByName(name);
    }

    @RequestMapping("/db/user/findbytoken")
    public User findUserByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println(token);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(userService.findUserByToken(token)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return userService.findUserByToken(token);
    }

    @RequestMapping("/db/user/findbyname")
    public User findUserByName(String name) {
        return userService.findByName(name);
    }

    @RequestMapping("/db/user/update")
    public Integer update(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO.toModel());
    }
}
