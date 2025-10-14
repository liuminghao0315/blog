package com.lmh.blog_project.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dto.BlogDTO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BlogApi {
    @Autowired
    private BlogService blogService;

    @RequestMapping("/db/blog/add")
    public Integer addBlog(@RequestBody BlogWithUserNameDTO blogWithUserNameDTO) {
        return blogService.add(blogWithUserNameDTO);
    }

    @RequestMapping("/db/blog/delete")
    public Integer deleteBlog(String id) {
        return blogService.delete(id);
    }

    @RequestMapping("/db/blog/findall")
    public Result<Paging<Blog>> findAll(Integer pageNum, Integer pageSize) {
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        return blogService.findAll(pageNum,pageSize);
    }

    @RequestMapping("/db/blog/findbyid")
    public Blog findBlogById(String id) {
        return blogService.findById(id);
    }

    @RequestMapping("/db/blog/update")
    public Integer update(@RequestBody BlogDTO blogDTO) {
        return blogService.update(blogDTO.toModel());
    }
}
