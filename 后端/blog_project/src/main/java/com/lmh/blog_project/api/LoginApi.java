package com.lmh.blog_project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.dao.UserDAO;
import com.lmh.blog_project.dataobject.UserDO;
import com.lmh.blog_project.dto.RegisterOrLoginFormDTO;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.service.UserService;
import com.lmh.blog_project.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
public class LoginApi {
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestBody RegisterOrLoginFormDTO registerOrLoginFormDTO) {
        UserDO savedUser = userDAO.findByName((registerOrLoginFormDTO.getUserName()));
        if (savedUser == null) {
            return "00";
        }
        String savedPassword = savedUser.getPassword();
        if(!savedPassword.equals(registerOrLoginFormDTO.getPassword())){
            return "01";
        }
        User user = userService.findByName(registerOrLoginFormDTO.getUserName());
        return tokenUtil.getToken(user);
    }

//    @RequestMapping("/logout")
//    @ResponseBody
//    public String logout(HttpServletRequest request) {
//
//    }

    @RequestMapping("/loginToken")
    public String testToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(tokenUtil.parseToken(token)!=null){
            try {
                return "请求成功，用户信息："+new ObjectMapper().writeValueAsString(tokenUtil.parseToken(token));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else{
            return "请求失败，token错误";
        }
    }
}
