package com.lmh.blog_project.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.entity.BlogsImages;
import com.lmh.blog_project.enums.BlogsImagesStatus;
import com.lmh.blog_project.service.BlogsImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class BlogsImagesApi {
    @Autowired
    private BlogsImagesService blogsImagesService;

    @RequestMapping("/upload/blogsImages")
    public Result<BlogsImages> uploadBlogsImages(@RequestBody MultipartFile file, @RequestParam("authorId")String authorId, @RequestParam("blogId")String blogId, @RequestParam("id")String id) {
        return blogsImagesService.uploadBlogsImages(file, authorId, blogId, id);
    }

    @RequestMapping("/deleteOne/blogsImages")
    public Boolean deleteBlogsOneImage(@RequestParam("fileName")String fileName, @RequestParam("authorId")String authorId) {
        return blogsImagesService.deleteBlogsOneImage(fileName, authorId);
    }

    @RequestMapping("/deleteSeveral/blogsImages")
    public Boolean deleteBlogsSeveralImage(@RequestParam("fileNames") List<String> fileNames, @RequestParam("authorId")String authorId) {
        return blogsImagesService.deleteBlogsSeveralImage(fileNames, authorId);
    }

    @RequestMapping("/updateSeveral/blogsImages")
    public Boolean updateBlogsSeveralImage(@RequestParam("fileNames") List<String> fileNames, @RequestParam("authorId")String authorId) {
        return blogsImagesService.updateBlogsSeveralImage(fileNames, authorId);
    }

    @RequestMapping("/deleteBlog/blogsImages")
    public Boolean deleteBlog(@RequestParam("blogId")String blogId) {
        return blogsImagesService.deleteBlog(blogId);
    }

    @RequestMapping("/deleteRedundantBlogsImages/blogsImages")
    public void deleteRedundantBlogsImages(@RequestParam("authorId")String authorId) {
        blogsImagesService.deleteRedundantBlogsImages(authorId);
    }
}
