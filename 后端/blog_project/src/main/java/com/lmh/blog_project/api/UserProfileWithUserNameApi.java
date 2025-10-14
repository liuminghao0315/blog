package com.lmh.blog_project.api;

import com.lmh.blog_project.dataobject.UserProfileDO;
import com.lmh.blog_project.dto.UserProfileWithUserNameDTO;
import com.lmh.blog_project.service.UserProfileService;
import com.lmh.blog_project.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserProfileWithUserNameApi {
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @RequestMapping("/wpwun/update")
    public Integer updateUserProfile(@RequestBody UserProfileWithUserNameDTO userProfileWithUserNameDTO) {
     UserProfileDO userProfileDO = new UserProfileDO();
        BeanUtils.copyProperties(userProfileWithUserNameDTO, userProfileDO);

        if(userProfileService.update(userProfileDO.toModel())==0
                ||
                userService.updateNameById(userProfileWithUserNameDTO.getUserId(), userProfileWithUserNameDTO.getUserName())==0){
            return 0;
        };
        return 1;
    }
}
