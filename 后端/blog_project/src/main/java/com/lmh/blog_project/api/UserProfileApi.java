package com.lmh.blog_project.api;

import com.lmh.blog_project.model.UserProfile;
import com.lmh.blog_project.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserProfileApi {
    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping("/userprofile")
    public UserProfile getUserProfile(String name) {
        return userProfileService.findByUserName(name);
    }
}
